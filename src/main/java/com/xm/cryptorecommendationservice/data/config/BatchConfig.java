package com.xm.cryptorecommendationservice.data.config;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.CryptoDto;
import com.xm.cryptorecommendationservice.common.repository.CryptoRepository;
import com.xm.cryptorecommendationservice.data.mapper.CryptoFieldSetMapper;
import com.xm.cryptorecommendationservice.data.policies.CryptoSkipPolicy;
import com.xm.cryptorecommendationservice.data.processor.CryptoProcessor;
import com.xm.cryptorecommendationservice.data.writer.CryptoWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final CryptoRepository cryptoRepository;

    @Value("classpath:/input/*.csv")
    private Resource[] resources;

    @Bean
    public Job readAndPersistJob() {
        return jobBuilderFactory
                .get("readAndPersistJob")
                .incrementer(new RunIdIncrementer())
                .start(readAndPersistStep())
                .build();
    }

    @Bean
    public Step readAndPersistStep() {
        CryptoSkipPolicy skipPolicy = new CryptoSkipPolicy();

        return stepBuilderFactory
                .get("readAndPersistStep")
                .<CryptoDto, Crypto>chunk(50)
                .reader(multiResourceItemReader())
                .processor(cryptoProcessor())
                .writer(cryptoWriter())
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .build();
    }

    @Bean
    public MultiResourceItemReader<CryptoDto> multiResourceItemReader()
    {
        MultiResourceItemReader<CryptoDto> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setDelegate(cryptoCsvReader());
        resourceItemReader.setResources(resources);

        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader<CryptoDto> cryptoCsvReader() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("timestamp", "symbol", "price");

        DefaultLineMapper<CryptoDto> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(new CryptoFieldSetMapper());

        FlatFileItemReader<CryptoDto> reader = new FlatFileItemReader<>();
        reader.setLineMapper(defaultLineMapper);
        reader.setLinesToSkip(1);

        return reader;
    }

    @Bean
    public ItemProcessor<CryptoDto, Crypto> cryptoProcessor() {
        return new CryptoProcessor();
    }

    @Bean
    public CryptoWriter<Crypto> cryptoWriter() {
        return new CryptoWriter<>(cryptoRepository);
    }

}
