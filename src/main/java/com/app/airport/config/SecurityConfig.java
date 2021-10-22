//package com.app.airport.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//        .withUser("airport")
//        .password("qwerty")
//        .authorities("ADMIN")
//        .and()
//        .withUser("appUser")
//        .password("qwerty")
//        .authorities("USER");
//  }
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//        .antMatchers("/flights")
//        .hasRole("USER")
//        .and()
//        .authorizeRequests()
//        .antMatchers("/flights", "/utils")
//        .hasRole("ADMIN");
//  }
//}
