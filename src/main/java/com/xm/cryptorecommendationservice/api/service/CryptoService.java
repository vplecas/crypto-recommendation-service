package com.xm.cryptorecommendationservice.api.service;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import com.xm.cryptorecommendationservice.common.exception.NoDataFoundException;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public interface CryptoService {

    /**
     * Finds and returns the oldest cryptocurrency entry for the specified symbol.
     *
     * @param symbol Crypto symbol
     * @throws NoDataFoundException If there are no persisted records
     * @return Crypto
     */
    public Optional<Crypto> findOldest(Symbol symbol);

    /**
     * Finds and returns the newest cryptocurrency entry for the specified symbol.
     *
     * @param symbol Cryptocurrency symbol
     * @throws NoDataFoundException If there are no persisted records
     * @return Crypto
     */
    public Optional<Crypto> findNewest(Symbol symbol);

    /**
     * Finds and returns the cryptocurrency with the minimum price value for the specified symbol.
     *
     * @param symbol Cryptocurrency symbol
     * @throws NoDataFoundException If there are no persisted records
     * @return Crypto
     */
    public Optional<Crypto> findMin(Symbol symbol);

    /**
     * Finds and returns the cryptocurrency with the maximum price value for the specified symbol.
     *
     * @param symbol Cryptocurrency symbol
     * @throws NoDataFoundException If there are no persisted records
     * @return Crypto
     */
    public Optional<Crypto> findMax(Symbol symbol);

    /**
     * Finds and returns a map of cryptocurrencies sorted by their normalized ranges.
     *
     * @param order The sorting order, either "asc" for ascending or "desc" for descending.
     * @return A map of the cryptocurrencies symbol and their corresponding normalized range.
     */
    public Map<String, BigDecimal> findSortedCryptosByNormalizedRange(String order);

    /**
     * Find cryptocurrency with highest normalized range for specified day.
     *
     * @param targetDate The target date for which the normalized range is calculated.
     * @return An entry representing the cryptocurrency symbol and its corresponding normalized range.
     */
    public Map.Entry<String, BigDecimal> findWithHighestNormalizedRange(LocalDate targetDate);
}
