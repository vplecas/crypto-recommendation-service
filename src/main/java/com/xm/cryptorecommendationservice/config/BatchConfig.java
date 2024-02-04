package com.xm.cryptorecommendationservice.config;

import com.xm.cryptorecommendationservice.domain.Crypto;
import com.xm.cryptorecommendationservice.domain.CryptoDto;
import com.xm.cryptorecommendationservice.mapper.CryptoFieldSetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

//    private final CryptoRepository cryptoRepository;

    @Value("classPath:/input/${input.fileName}")
    private Resource inputFile;

    @Bean
    public Job readAndPersistJob() {
        System.out.println("VELJKOOOOO");

        return jobBuilderFactory
                .get("readAndPersistJob")
                .incrementer(new RunIdIncrementer())
                .start(readAndPersistStep())
                .build();
    }

    @Bean
    public Step readAndPersistStep() {
        CryptoSkipPolicy skipPolicy = new CryptoSkipPolicy();
        System.out.println("VELJKOOOOO");

        return stepBuilderFactory
                .get("readAndPersistStep")
                .<CryptoDto, Crypto>chunk(50)
                .reader(cryptoCsvReader())
                .processor(cryptoProcessor())
                .writer(cryptoWriter())
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public FlatFileItemReader<CryptoDto> cryptoCsvReader() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        System.out.println("VELJKOOOOO");
        delimitedLineTokenizer.setNames("timestamp", "symbol", "price");

        DefaultLineMapper<CryptoDto> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(new CryptoFieldSetMapper());

        FlatFileItemReader<CryptoDto> reader = new FlatFileItemReader<>();
        reader.setLineMapper(defaultLineMapper);
        reader.setResource(inputFile);
        reader.setLinesToSkip(1);

        return reader;
    }

    @Bean
    public ItemProcessor<CryptoDto, Crypto> cryptoProcessor() {
        return new CryptoProcessor();
    }

    @Bean
    public CryptoWriter<Crypto> cryptoWriter() {
        System.out.println("VELJKOOOOO");

        return new CryptoWriter<>();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(16);
        executor.setThreadNamePrefix("task exec. - ");
        executor.initialize();

        return executor;
    }

}
