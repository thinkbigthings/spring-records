package com.thinkbigthings.springrecords;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(prefix = "app")
public record ConfigurationRecord(int number, UserField field, @NotNull UserPage page) {

    public record UserField(String name, int depth) {  }
    public record UserPage(int size) {   }

}






