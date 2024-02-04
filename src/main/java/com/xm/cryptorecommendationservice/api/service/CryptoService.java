package com.xm.cryptorecommendationservice.api.service;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import com.xm.cryptorecommendationservice.common.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoService {

    private final CryptoRepository cryptoRepository;

    public Crypto findOldest(Symbol symbol) {
        return cryptoRepository.findOldest(symbol.name()).get();
    }

    public Crypto findNewest(Symbol symbol) {
        return cryptoRepository.findNewest(symbol.name()).get();
    }

    public Crypto findMin(Symbol symbol) {
        return cryptoRepository.findMin(symbol.name()).get();
    }

    public Crypto findMax(Symbol symbol) {
        return cryptoRepository.findMax(symbol.name()).get();
    }
}
