package com.app.aiport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.aiport.entity.Flight;
import com.app.aiport.service.FlightsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Flight> saveFlight(@RequestBody @Valid Flight flight) {
        return ResponseEntity.ok(flightsService.saveNewFlight(flight));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Integer id) {
        return ResponseEntity.ok(flightsService.deleteFlightById(id));
    }
}
