package com.xm.cryptorecommendationservice.common.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Symbol {

    BTC("Bitcoin"),
    DOGE("Dogecoin"),
    ETH("Ethereum"),
    LTC("Litecoin"),
    XRP("XRP");

    private final String label;

    Symbol(String label) {
        this.label = label;
    }

    @JsonValue
    public String getJsonValue() {
        return this.name().toLowerCase();
    }

    public String getLabel() {
        return this.label;
    }
}
