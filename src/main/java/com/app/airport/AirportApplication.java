package com.app.airport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Project main class. */
@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class AirportApplication {

  public static void main(String[] args) {
    SpringApplication.run(AirportApplication.class, args);
  }
}
