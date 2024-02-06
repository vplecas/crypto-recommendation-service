package com.xm.cryptorecommendationservice.common.mapper;

import com.xm.cryptorecommendationservice.common.domain.CryptoDto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.Instant;
import java.time.ZoneId;

public class CryptoFieldSetMapper implements FieldSetMapper<CryptoDto> {

    @Override
    public CryptoDto mapFieldSet(FieldSet fieldSet) throws BindException {
        return CryptoDto.builder()
                .timestamp(Instant.ofEpochMilli(fieldSet.readLong("timestamp")).atZone(ZoneId.of("UTC")))
                .symbol(Symbol.valueOf(fieldSet.readString("symbol").toUpperCase()))
                .price(fieldSet.readBigDecimal("price"))
                .build();
    }

}
