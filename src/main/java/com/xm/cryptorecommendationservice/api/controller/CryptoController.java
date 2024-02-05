package com.xm.cryptorecommendationservice.api.controller;

import com.xm.cryptorecommendationservice.api.service.CryptoService;
import com.xm.cryptorecommendationservice.common.converter.CaseInsensitiveDataConverter;
import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Symbol.class, new CaseInsensitiveDataConverter<>(Symbol.class));
    }

    @GetMapping(path = {"/sorted", "/sorted/{order}"})
    private MappingJacksonValue findSortedCryptos(@PathVariable("order") String order) {
        return new MappingJacksonValue(cryptoService.findSortedCryptosByNormalizedRange(order));
    }

    @GetMapping("/{symbol}/oldest")
    private MappingJacksonValue findOldestCrypto(@PathVariable("symbol") Symbol symbol) {
        return new MappingJacksonValue(cryptoService.findOldest(symbol));
    }

    @GetMapping("/{symbol}/newest")
    private MappingJacksonValue findNewestCrypto(@PathVariable("symbol") Symbol symbol) {
        return new MappingJacksonValue(cryptoService.findNewest(symbol));
    }

    @GetMapping("/{symbol}/min")
    private MappingJacksonValue findMinCrypto(@PathVariable("symbol") Symbol symbol) {
        return new MappingJacksonValue(cryptoService.findMin(symbol));
    }

    @GetMapping("/{symbol}/max")
    private MappingJacksonValue findMaxCrypto(@PathVariable("symbol") Symbol symbol) {
        return new MappingJacksonValue(cryptoService.findMax(symbol));
    }

    @GetMapping("/highestNormalizedRange/{date}")
    private MappingJacksonValue findCryptoWithHighestNormalizeRange(
            @PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate targetDate) {
        return new MappingJacksonValue(cryptoService.findWithHighestNormalizedRange(targetDate));
    }

}
