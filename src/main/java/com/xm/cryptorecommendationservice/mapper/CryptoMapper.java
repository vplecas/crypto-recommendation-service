package com.xm.cryptorecommendationservice.mapper;

import com.xm.cryptorecommendationservice.domain.Crypto;
import com.xm.cryptorecommendationservice.domain.CryptoDto;

public class CryptoMapper {

    private CryptoMapper() {
        // private constructor to disable instantiation of mapper/util class
    }

    public static CryptoDto toDto(Crypto crypto) {
        return CryptoDto.builder()
                .id(crypto.getId())
                .timestamp(crypto.getTimestamp())
                .symbol(crypto.getSymbol())
                .price(crypto.getPrice())
                .build();
    }

}
