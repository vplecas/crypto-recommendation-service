package com.xm.cryptorecommendationservice.api.service.impl;

import com.xm.cryptorecommendationservice.api.service.CryptoService;
import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import com.xm.cryptorecommendationservice.common.exception.NoDataFoundException;
import com.xm.cryptorecommendationservice.common.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoServiceImpl.class);

    @Override
    public Optional<Crypto> findOldest(Symbol symbol) {
        Optional<Crypto> optCrypto = cryptoRepository.findOldest(symbol.name());
        if (optCrypto.isEmpty()) {
            LOGGER.warn("There are no records for {} crypto.", symbol.getLabel());
            return Optional.empty();
        }
        return optCrypto;
    }

    @Override
    public Optional<Crypto> findNewest(Symbol symbol) {
        Optional<Crypto> optCrypto = cryptoRepository.findNewest(symbol.name());
        if (optCrypto.isEmpty()) {
            LOGGER.warn("There are no records for {} crypto.", symbol.getLabel());
            return Optional.empty();
        }
        return optCrypto;
    }

    @Override
    public Optional<Crypto> findMin(Symbol symbol) {
        Optional<Crypto> optCrypto = cryptoRepository.findMin(symbol.name());
        if (optCrypto.isEmpty()) {
            LOGGER.warn("There are no records for {} crypto.", symbol.getLabel());
            return Optional.empty();
        }
        return optCrypto;
    }

    @Override
    public Optional<Crypto> findMax(Symbol symbol) {
        Optional<Crypto> optCrypto = cryptoRepository.findMax(symbol.name());
        if (optCrypto.isEmpty()) {
            LOGGER.warn("There are no records for {} crypto.", symbol.getLabel());
            return Optional.empty();
        }
        return optCrypto;
    }

    @Override
    public Map<String, BigDecimal> findSortedCryptosByNormalizedRange(String order) {
        if (StringUtils.isEmpty(order)) {
            throw new IllegalArgumentException("Order value must not be empty.");
        }
        Map<String, BigDecimal> cryptos = new LinkedHashMap<>();
        for (Symbol symbol : Symbol.values()) {
            BigDecimal normalizedRange = calculateNormalizedRange(symbol);
            if (ObjectUtils.isNotEmpty(normalizedRange)) {
                cryptos.put(symbol.getLabel(), calculateNormalizedRange(symbol));
            }
        }

        return cryptos.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(order.equalsIgnoreCase("asc") ?
                        Map.Entry.<String, BigDecimal>comparingByValue().reversed() : Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public Entry<String, BigDecimal> findWithHighestNormalizedRange(LocalDate targetDate) {
        Map<String, BigDecimal> cryptos = new LinkedHashMap<>();
        for (Symbol symbol : Symbol.values()) {
            BigDecimal normalisedRange = calculateNormalizedRange(symbol, targetDate);
            if (ObjectUtils.isNotEmpty(normalisedRange)) {
                cryptos.put(symbol.getLabel(), calculateNormalizedRange(symbol, targetDate));
            }
        }

        if (cryptos.isEmpty()) {
            throw new NoDataFoundException("No data found for the highest normalized range on the specified date.");
        }

        return cryptos.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
                .entrySet().iterator().next();
    }

    /**
     * Calculate and return crypto normalized range on a specified target date
     * and for specified symbol. If there is no data return null.
     *
     * @param symbol     The cryptocurrency symbol for which to calculate the normalized range.
     * @param targetDate The target date for which to calculate the normalized range.
     * @return The normalized range for the specified cryptocurrency on specified date, otherwise return null.
     */
    private BigDecimal calculateNormalizedRange(Symbol symbol, LocalDate targetDate) {
        Optional<Crypto> optCryptoMin = findMinByDate(symbol, targetDate);
        Optional<Crypto> optCryptoMax = findMaxByDate(symbol, targetDate);

        if (optCryptoMin.isEmpty() || optCryptoMax.isEmpty()) {
            return null;
        }

        BigDecimal min = optCryptoMin.get().getPrice();
        BigDecimal max = optCryptoMax.get().getPrice();

        return calculateNormalizeRange(min, max);
    }

    /**
     * Calculate and return crypto normalized range for specified symbol.
     * If there is no data then return null.
     *
     * @param symbol The cryptocurrency symbol for which to calculate the normalized range.
     * @return The normalized range if crypto is present, otherwise return null.
     */
    private BigDecimal calculateNormalizedRange(Symbol symbol) {
        Optional<Crypto> optCryptoMin = findMin(symbol);
        Optional<Crypto> optCryptoMax = findMax(symbol);

        if (optCryptoMin.isEmpty() || optCryptoMax.isEmpty()) {
            return null;
        }

        BigDecimal min = optCryptoMin.get().getPrice();
        BigDecimal max = optCryptoMax.get().getPrice();

        return calculateNormalizeRange(min, max);
    }

    private BigDecimal calculateNormalizeRange(BigDecimal min, BigDecimal max) {
        return max.add(min.negate()).divide(min, 4, RoundingMode.CEILING);
    }

    private Optional<Crypto> findMinByDate(Symbol symbol, LocalDate targetDate) {
        return cryptoRepository.findMinByDate(symbol.name(), targetDate);
    }

    private Optional<Crypto> findMaxByDate(Symbol symbol, LocalDate targetDate) {
        return cryptoRepository.findMaxByDate(symbol.name(), targetDate);
    }

}
