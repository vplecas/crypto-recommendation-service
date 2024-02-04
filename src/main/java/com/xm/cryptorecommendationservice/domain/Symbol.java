package com.xm.cryptorecommendationservice.domain;

public enum Symbol {

    BTC("Bitcoin"),
    DOGE("Dogecoin"),
    ETHEREUM("Ethereum"),
    LITECOIN("Litecoin"),
    XRP("XRP");

    private final String label;

    Symbol(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
