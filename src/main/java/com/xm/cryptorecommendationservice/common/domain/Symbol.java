package com.xm.cryptorecommendationservice.common.domain;

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

    public String getLabel() {
        return this.label;
    }
}
