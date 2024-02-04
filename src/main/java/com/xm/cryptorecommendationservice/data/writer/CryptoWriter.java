package com.xm.cryptorecommendationservice.data.writer;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.Nonnull;
import java.util.List;

@RequiredArgsConstructor
public class CryptoWriter<T> implements ItemWriter<Crypto> {

    private final CryptoRepository cryptoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoWriter.class);

    @Override
    public void write(@Nonnull List<? extends Crypto> items) {
        LOGGER.debug("{} Start", getClass().getSimpleName());
        LOGGER.debug("There are {} records to write.", items.size());
        cryptoRepository.saveAll(items);
    }
}
