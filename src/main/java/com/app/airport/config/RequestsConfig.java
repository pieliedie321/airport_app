package com.app.airport.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="taco.orders")
public class RequestsConfig {

  private int elementsOnResponse = 50;
}
