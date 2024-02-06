package com.xm.cryptorecommendationservice.api.controller;

import com.xm.cryptorecommendationservice.api.service.CryptoService;
import com.xm.cryptorecommendationservice.common.converter.CaseInsensitiveDataConverter;
import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import com.xm.cryptorecommendationservice.common.exception.NoDataFoundException;
import com.xm.cryptorecommendationservice.common.mapper.CryptoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Symbol.class, new CaseInsensitiveDataConverter<>(Symbol.class));
    }

    @GetMapping("/sorted/{order}")
    private MappingJacksonValue findSortedCryptos(@PathVariable("order") String order) {
        return new MappingJacksonValue(cryptoService.findSortedCryptosByNormalizedRange(order));
    }

    @GetMapping("/{symbol}/oldest")
    private MappingJacksonValue findOldestCrypto(@PathVariable("symbol") Symbol symbol) {
        Optional<Crypto> optCrypto = cryptoService.findOldest(symbol);

        if (optCrypto.isEmpty()) {
            throw new NoDataFoundException(String.format("There is no %s cryptocurrency", symbol.name()));
        }

        return new MappingJacksonValue(CryptoMapper.toDto(optCrypto.get()));
    }

    @GetMapping("/{symbol}/newest")
    private MappingJacksonValue findNewestCrypto(@PathVariable("symbol") Symbol symbol) {
        Optional<Crypto> optCrypto = cryptoService.findNewest(symbol);

        if (optCrypto.isEmpty()) {
            throw new NoDataFoundException(String.format("There is no %s cryptocurrency", symbol.name()));
        }

        return new MappingJacksonValue(CryptoMapper.toDto(optCrypto.get()));
    }

    @GetMapping("/{symbol}/min")
    private MappingJacksonValue findMinCrypto(@PathVariable("symbol") Symbol symbol) {
        Optional<Crypto> optCrypto = cryptoService.findMin(symbol);

        if (optCrypto.isEmpty()) {
            throw new NoDataFoundException(String.format("There is no %s cryptocurrency", symbol.name()));
        }

        return new MappingJacksonValue(CryptoMapper.toDto(optCrypto.get()));
    }

    @GetMapping("/{symbol}/max")
    private MappingJacksonValue findMaxCrypto(@PathVariable("symbol") Symbol symbol) {
        Optional<Crypto> optCrypto = cryptoService.findMax(symbol);

        if (optCrypto.isEmpty()) {
            throw new NoDataFoundException(String.format("There is no %s cryptocurrency", symbol.name()));
        }

        return new MappingJacksonValue(CryptoMapper.toDto(optCrypto.get()));
    }

    @GetMapping("/highestNormalizedRange/{date}")
    private MappingJacksonValue findCryptoWithHighestNormalizeRange(
            @PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate targetDate) {
        return new MappingJacksonValue(cryptoService.findWithHighestNormalizedRange(targetDate));
    }

}
