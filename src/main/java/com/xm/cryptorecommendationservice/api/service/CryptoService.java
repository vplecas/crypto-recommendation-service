package com.xm.cryptorecommendationservice.api.service;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;

import java.time.LocalDate;
import java.util.Map;

public interface CryptoService {

    /**
     * Finds and returns the oldest cryptocurrency entry for the specified symbol.
     *
     * @param symbol Crypto symbol
     * @return Crypto
     */
    public Crypto findOldest(Symbol symbol);

    /**
     * Finds and returns the newest cryptocurrency entry for the specified symbol.
     *
     * @param symbol Cryptocurrency symbol
     * @return Crypto
     */
    public Crypto findNewest(Symbol symbol);

    /**
     * Finds and returns the cryptocurrency with the minimum price value for the specified symbol.
     *
     * @param symbol Cryptocurrency symbol
     * @return Crypto
     */
    public Crypto findMin(Symbol symbol);

    /**
     * Finds and returns the cryptocurrency with the maximum price value for the specified symbol.
     *
     * @param symbol Cryptocurrency symbol
     * @return Crypto
     */
    public Crypto findMax(Symbol symbol);

    /**
     * Finds and returns a map of cryptocurrencies sorted by their normalized ranges.
     *
     * @param order The sorting order, either "asc" for ascending or "desc" for descending.
     * @return A map of the cryptocurrencies symbol and their corresponding normalized range.
     */
    public Map<String, Double> findSortedCryptosByNormalizedRange(String order);

    /**
     * Find cryptocurrency with highest normalized range for specified day.
     *
     * @param targetDate The target date for which the normalized range is calculated.
     * @return An entry representing the cryptocurrency symbol and its corresponding normalized range.
     */
    public Map.Entry<String, Double> findWithHighestNormalizedRange(LocalDate targetDate);
}
