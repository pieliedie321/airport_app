package com.app.aiport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.aiport.entity.Flight;
import com.app.aiport.service.FlightsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST контроллер для взаимодействия с flights репо.
 */
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

  @GetMapping("/airports")
  public List<Flight> getFlightsByAirports(@RequestParam(required = false) String arrival,
                                           @RequestParam(required = false) String departure) {
    return flightsService.getFlightsByAirport(arrival, departure);
  }

  @GetMapping("/status/{status}")
  public List<Flight> getFlightsByStatus(@PathVariable String status) {
    return flightsService.getFlightsByStatus(status);
  }

  @GetMapping("/id/{id}")
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
