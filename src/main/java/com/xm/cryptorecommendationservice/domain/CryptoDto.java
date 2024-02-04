package com.xm.cryptorecommendationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoDto {

    private Long id;
    private ZonedDateTime timestamp;
    private Symbol symbol;
    private BigDecimal price;

}
