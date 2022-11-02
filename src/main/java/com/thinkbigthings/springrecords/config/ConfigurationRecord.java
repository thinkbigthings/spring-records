package com.thinkbigthings.springrecords.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
@ConfigurationProperties(prefix = "app")
public record ConfigurationRecord(int number, Page page, @NotNull Data data) {

    public record Data(Boolean useRepo) {   }

    public record Page(@Positive int maxSize) {   }

}






