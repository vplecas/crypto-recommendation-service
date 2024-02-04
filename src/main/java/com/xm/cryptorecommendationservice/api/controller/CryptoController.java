package com.xm.cryptorecommendationservice.api.controller;

import com.xm.cryptorecommendationservice.api.service.CryptoService;
import com.xm.cryptorecommendationservice.common.converter.CaseInsensitiveDataConverter;
import com.xm.cryptorecommendationservice.common.domain.Symbol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @InitBinder("symbol")
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Symbol.class, new CaseInsensitiveDataConverter<>(Symbol.class));
    }

    @GetMapping("/sorted/{order}")
    private MappingJacksonValue searchCryptos(@PathVariable("order") String order) {
        return new MappingJacksonValue("it works");
    }

    @GetMapping("/{symbol}/oldest")
    private MappingJacksonValue searchOldestCrypto(@PathVariable("symbol") Symbol symbol) {
        return new MappingJacksonValue(cryptoService.findOldest(symbol));
    }

    @GetMapping("/{symbol}/newest")
    private MappingJacksonValue searchNewestCrypto(@PathVariable("symbol") Symbol symbol) {
        return new MappingJacksonValue(cryptoService.findNewest(symbol));
    }

    @GetMapping("/{symbol}/min")
    private MappingJacksonValue searchMinCrypto(@PathVariable("symbol") Symbol symbol) {
        return new MappingJacksonValue(cryptoService.findMin(symbol));
    }

    @GetMapping("/{symbol}/max")
    private MappingJacksonValue searchMaxCrypto(@PathVariable("symbol") Symbol symbol) {
        return new MappingJacksonValue(cryptoService.findMax(symbol));
    }

}
