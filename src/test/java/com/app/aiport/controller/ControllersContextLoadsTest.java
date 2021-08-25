package com.app.aiport.controller;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ControllersContextLoadsTest {

    @Autowired
    private AirportsController airportsController;

    @Autowired
    private AircraftsController aircraftsController;

    @Autowired
    private BoardingPassesController passesController;

    @Autowired
    private BookingsController bookingsController;

    @Autowired
    private FlightsController flightsController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(airportsController).isNotNull();
        assertThat(aircraftsController).isNotNull();
        assertThat(bookingsController).isNotNull();
        assertThat(passesController).isNotNull();
        assertThat(flightsController).isNotNull();
    }
}
