package com.xm.cryptorecommendationservice.data.processor;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.CryptoDto;
import com.xm.cryptorecommendationservice.common.exception.BatchSkipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

public class CryptoProcessor implements ItemProcessor<CryptoDto, Crypto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoProcessor.class);

    @Override
    public Crypto process(@Nonnull CryptoDto cryptoDto) {
        if (!isValid(cryptoDto)) {
            LOGGER.warn("Error while trying to parse CryptoDto to Crypto. Skip record: {}", cryptoDto);
            throw new BatchSkipException("Could not process record.");
        }
        return Crypto.builder()
                .timestamp(cryptoDto.getTimestamp())
                .symbol(cryptoDto.getSymbol())
                .price(cryptoDto.getPrice())
                .build();
    }

    private boolean isValid(CryptoDto cryptoDto) {
        return cryptoDto.getTimestamp() != null &&
                cryptoDto.getSymbol() != null &&
                cryptoDto.getPrice() != null &&
                // The BigDecimal is higher than 0
                cryptoDto.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

}
