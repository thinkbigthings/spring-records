package com.thinkbigthings.springrecords;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "app")
public record ConfigurationRecord(int number) {}






