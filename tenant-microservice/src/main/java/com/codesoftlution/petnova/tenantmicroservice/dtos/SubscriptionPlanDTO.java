package com.codesoftlution.petnova.tenantmicroservice.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriptionPlanDTO {
    private Long id;
    private String name;
    private BigDecimal priceUsd;
    private String interval;
    private String features;
    private long subscribers;
}
