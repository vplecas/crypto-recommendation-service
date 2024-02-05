package com.xm.cryptorecommendationservice.api.service.impl;

import com.xm.cryptorecommendationservice.api.service.CryptoService;
import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import com.xm.cryptorecommendationservice.common.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;

    @Override
    public Crypto findOldest(Symbol symbol) {
        return cryptoRepository.findOldest(symbol.name()).get();
    }

    @Override
    public Crypto findNewest(Symbol symbol) {
        return cryptoRepository.findNewest(symbol.name()).get();
    }

    @Override
    public Crypto findMin(Symbol symbol) {
        return cryptoRepository.findMin(symbol.name()).get();
    }

    @Override
    public Crypto findMax(Symbol symbol) {
        return cryptoRepository.findMax(symbol.name()).get();
    }

    @Override
    public Map<String, Double> findSortedCryptosByNormalizedRange(String order) {
        Map<String, Double> cryptos = new LinkedHashMap<>();
        for (Symbol symbol : Symbol.values()) {
            cryptos.put(symbol.getLabel(), calculateNormalizedRange(symbol));
        }

        return cryptos.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(order.equalsIgnoreCase("asc") ?
                        Map.Entry.<String, Double>comparingByValue().reversed() : Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public Entry<String, Double> findWithHighestNormalizedRange(LocalDate targetDate) {
        Map<String, Double> cryptos = new LinkedHashMap<>();
        for (Symbol symbol : Symbol.values()) {
            cryptos.put(symbol.getLabel(), calculateNormalizedRange(symbol, targetDate));
        }

        return cryptos.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
                .entrySet().iterator().next();
    }

    private Double calculateNormalizedRange(Symbol symbol, LocalDate targetDate) {
        double min = findMinByDate(symbol, targetDate).getPrice().doubleValue();
        double max = findMaxByDate(symbol, targetDate).getPrice().doubleValue();

        return calculateNormalizeRange(min, max);
    }

    private Double calculateNormalizedRange(Symbol symbol) {
        double min = findMin(symbol).getPrice().doubleValue();
        double max = findMax(symbol).getPrice().doubleValue();

        return calculateNormalizeRange(min, max);
    }

    private double calculateNormalizeRange(double min, double max) {
        return (max - min) / min;
    }

    private Crypto findMinByDate(Symbol symbol, LocalDate targetDate) {
        return cryptoRepository.findMinByDate(symbol.name(), targetDate).get();
    }

    private Crypto findMaxByDate(Symbol symbol, LocalDate targetDate) {
        return cryptoRepository.findMaxByDate(symbol.name(), targetDate).get();
    }

}
