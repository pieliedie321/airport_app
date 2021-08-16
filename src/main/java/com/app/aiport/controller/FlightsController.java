package com.app.aiport.controller;

import com.app.aiport.entity.Flight;
import com.app.aiport.service.flights.FlightsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/flights")
@AllArgsConstructor
public class FlightsController {

    private final FlightsService flightsService;

    @GetMapping
    public List<Flight> getAllFlights(@RequestParam(required = false) String flightNo) {
        return flightsService.getFlights(flightNo);
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Integer id) {
        return flightsService.getFlightById(id);
    }
}
