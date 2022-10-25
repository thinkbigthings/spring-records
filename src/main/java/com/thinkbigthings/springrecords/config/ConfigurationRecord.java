package com.thinkbigthings.springrecords.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "app")
public record ConfigurationRecord(int number, @NotNull UserPage page, UserField field) {

    public record UserPage(int size) {   }

    public record UserField(String name, int depth) {  }

}






