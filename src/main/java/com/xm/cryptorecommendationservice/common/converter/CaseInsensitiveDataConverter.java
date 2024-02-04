package com.xm.cryptorecommendationservice.common.converter;

import com.xm.cryptorecommendationservice.common.exception.InvalidCryptoSymbolException;

import java.beans.PropertyEditorSupport;

public class CaseInsensitiveDataConverter<T extends Enum<T>> extends PropertyEditorSupport {
    private final Class<T> typeParameterClass;

    public CaseInsensitiveDataConverter(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public void setAsText(String text) {
        try {
            this.setValue(Enum.valueOf(this.typeParameterClass, text.toUpperCase()));
        } catch (IllegalArgumentException e) {
            this.setValue(null);
            throw new InvalidCryptoSymbolException("Invalid crypto symbol: " + text);
        }
    }
}
