package com.thinkbigthings.springrecords.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@ConfigurationProperties(prefix = "app")
public record ConfigurationRecord(int number, @NotNull Page page) {

    public record Page(@Positive int maxSize) {   }

    // application.properties
    //
    //    app.number=99
    //    app.page.maxSize=12

}






