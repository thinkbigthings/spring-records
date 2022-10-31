package com.thinkbigthings.springrecords.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "app")
public record ConfigurationRecord(int number, Page page, @NotNull Strategy strategy) {

    public record Strategy(Boolean dao) {   }

    public record Page(int size) {   }

}






