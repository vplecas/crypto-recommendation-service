package com.xm.cryptorecommendationservice.service;

import com.xm.cryptorecommendationservice.api.service.CryptoService;
import com.xm.cryptorecommendationservice.api.service.impl.CryptoServiceImpl;
import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import com.xm.cryptorecommendationservice.common.exception.NoDataFoundException;
import com.xm.cryptorecommendationservice.common.repository.CryptoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CryptoServiceTest {

    @Mock
    private CryptoRepository cryptoRepository;

    private CryptoService cryptoService;

    @BeforeEach
    void init() {
        cryptoRepository = mock(CryptoRepository.class);
        cryptoService = new CryptoServiceImpl(cryptoRepository);
    }

    @Test
    void getMinCrypto() {
        Optional<Crypto> expectedCrypto = Optional.of(Crypto.builder().id(1L).symbol(Symbol.DOGE).price(new BigDecimal(45000)).build());
        when(cryptoRepository.findMin(Symbol.DOGE.name())).thenReturn(expectedCrypto);

        Optional<Crypto> crypto = cryptoService.findMin(Symbol.DOGE);

        assertTrue(crypto.isPresent());
        assertEquals(crypto, expectedCrypto);
    }

    @Test
    void getMinCryptoIfEmpty() {
        when(cryptoRepository.findMin(Symbol.DOGE.name())).thenReturn(Optional.empty());

        Optional<Crypto> crypto = cryptoService.findMin(Symbol.DOGE);

        assertFalse(crypto.isPresent());
    }

    @Test
    void getMaxCrypto() {
        Optional<Crypto> expectedCrypto = Optional.of(Crypto.builder().id(1L).symbol(Symbol.BTC).price(new BigDecimal(45000)).build());
        when(cryptoRepository.findMin(Symbol.BTC.name())).thenReturn(expectedCrypto);

        Optional<Crypto> crypto = cryptoService.findMin(Symbol.BTC);

        assertTrue(crypto.isPresent());
        assertEquals(crypto, expectedCrypto);
    }

    @Test
    void getMaxCryptoIfEmpty() {
        when(cryptoRepository.findMax(Symbol.DOGE.name())).thenReturn(Optional.empty());

        Optional<Crypto> crypto = cryptoService.findMax(Symbol.DOGE);

        assertFalse(crypto.isPresent());
    }

    @Test
    void getOldestCrypto() {
        Optional<Crypto> expectedCrypto = Optional.of(Crypto.builder().id(1L).symbol(Symbol.BTC).price(new BigDecimal(45000)).timestamp(ZonedDateTime.now()).build());
        when(cryptoRepository.findOldest(Symbol.BTC.name())).thenReturn(expectedCrypto);

        Optional<Crypto> crypto = cryptoService.findOldest(Symbol.BTC);

        assertTrue(crypto.isPresent());
        assertEquals(crypto, expectedCrypto);
    }

    @Test
    void getOldestCryptoIfEmpty() {
        when(cryptoRepository.findOldest(Symbol.DOGE.name())).thenReturn(Optional.empty());

        Optional<Crypto> crypto = cryptoService.findOldest(Symbol.DOGE);

        assertFalse(crypto.isPresent());
    }

    @Test
    void getNewestCrypto() {
        Optional<Crypto> expectedCrypto = Optional.of(Crypto.builder().id(1L).symbol(Symbol.BTC).price(new BigDecimal(45000)).timestamp(ZonedDateTime.now()).build());
        when(cryptoRepository.findNewest(Symbol.BTC.name())).thenReturn(expectedCrypto);

        Optional<Crypto> crypto = cryptoService.findNewest(Symbol.BTC);

        assertTrue(crypto.isPresent());
        assertEquals(crypto, expectedCrypto);
    }

    @Test
    void getNewestCryptoIfEmpty() {
        when(cryptoRepository.findNewest(Symbol.DOGE.name())).thenReturn(Optional.empty());

        Optional<Crypto> crypto = cryptoService.findNewest(Symbol.DOGE);

        assertFalse(crypto.isPresent());
    }

    @Test
    void findWithHighestNormalizedRange() {
        LocalDate curDate = LocalDate.now();
        when(cryptoRepository.findMinByDate(Symbol.ETH.name(), curDate)).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(3000)).symbol(Symbol.ETH).build()));
        when(cryptoRepository.findMaxByDate(Symbol.ETH.name(), curDate)).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(4000)).symbol(Symbol.ETH).build()));
        when(cryptoRepository.findMinByDate(Symbol.XRP.name(), curDate)).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(3)).symbol(Symbol.XRP).build()));
        when(cryptoRepository.findMaxByDate(Symbol.XRP.name(), curDate)).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(5)).symbol(Symbol.XRP).build()));

        Map.Entry<String, BigDecimal> entry = cryptoService.findWithHighestNormalizedRange(curDate);

        assertEquals(entry.getKey(), Symbol.XRP.getLabel());
    }

    @Test
    void findWithHighestNormalizedRangeNoRecords() {
        LocalDate curDate = LocalDate.now();
        when(cryptoRepository.findMinByDate(Symbol.ETH.name(), curDate)).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(3000)).symbol(Symbol.ETH).build()));
        when(cryptoRepository.findMaxByDate(Symbol.ETH.name(), curDate)).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(4000)).symbol(Symbol.ETH).build()));
        when(cryptoRepository.findMinByDate(Symbol.XRP.name(), curDate)).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(3)).symbol(Symbol.XRP).build()));
        when(cryptoRepository.findMaxByDate(Symbol.XRP.name(), curDate)).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(5)).symbol(Symbol.XRP).build()));

        assertThrows(NoDataFoundException.class,
                () -> cryptoService.findWithHighestNormalizedRange(curDate.minusDays(2)));
    }

    @Test
    void findSortedCryptosByNormalizedRangeAsc() {
        // normalised range for eth is 0.333
        when(cryptoRepository.findMin(Symbol.ETH.name())).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(3000)).symbol(Symbol.ETH).build()));
        when(cryptoRepository.findMax(Symbol.ETH.name())).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(4000)).symbol(Symbol.ETH).build()));
        // normalised range for xrp is 0.666
        when(cryptoRepository.findMin(Symbol.XRP.name())).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(3)).symbol(Symbol.XRP).build()));
        when(cryptoRepository.findMax(Symbol.XRP.name())).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(5)).symbol(Symbol.XRP).build()));

        Map<String, BigDecimal> cryptos = cryptoService.findSortedCryptosByNormalizedRange("asc");

        assertEquals(2, cryptos.size());
        assertEquals(Symbol.ETH.getLabel(), cryptos.entrySet().iterator().next().getKey());
    }

    @Test
    void findSortedCryptosByNormalizedRangeDesc() {
        // normalised range for eth is 0.333
        when(cryptoRepository.findMin(Symbol.ETH.name())).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(3000)).symbol(Symbol.ETH).build()));
        when(cryptoRepository.findMax(Symbol.ETH.name())).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(4000)).symbol(Symbol.ETH).build()));
        // normalised range for xrp is 0.666
        when(cryptoRepository.findMin(Symbol.XRP.name())).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(3)).symbol(Symbol.XRP).build()));
        when(cryptoRepository.findMax(Symbol.XRP.name())).thenReturn(Optional.of(Crypto.builder().price(new BigDecimal(5)).symbol(Symbol.XRP).build()));

        Map<String, BigDecimal> cryptos = cryptoService.findSortedCryptosByNormalizedRange("desc");

        assertEquals(2, cryptos.size());
        assertEquals(Symbol.XRP.getLabel(), cryptos.entrySet().iterator().next().getKey());
    }

    @Test
    void findSortedCryptosByNormalizedRangeWithEmptyOrder() {
        assertThrows(IllegalArgumentException.class,
                () -> cryptoService.findSortedCryptosByNormalizedRange(null));
    }

}
