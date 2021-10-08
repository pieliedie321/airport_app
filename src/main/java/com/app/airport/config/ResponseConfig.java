package com.app.airport.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="flights.response")
public class ResponseConfig {

    private int limit = 25;
}
