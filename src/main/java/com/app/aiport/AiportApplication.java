package com.app.aiport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Мейн класс проекта. */
@SpringBootApplication
@EnableTransactionManagement
public class AiportApplication {

  public static void main(String[] args) {
    SpringApplication.run(AiportApplication.class, args);
  }
}
