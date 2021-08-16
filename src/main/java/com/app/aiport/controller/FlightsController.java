package com.app.aiport.controller;

import com.app.aiport.entity.Flight;
import com.app.aiport.service.flights.FlightsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/flights")
@AllArgsConstructor
public class FlightsController {

    private final FlightsService flightsService;

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightsService.getAllFlights();
    }
}
