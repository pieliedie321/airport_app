package com.app.airport.controller.other;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import com.app.airport.entity.Flight;
import com.app.airport.service.FlightsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for flights repo. */
@Slf4j
@RestController
@RequestMapping(value = "/flights", produces = "application/json")
public class FlightsController {

  private final FlightsService service;

  @Autowired
  public FlightsController(FlightsService service) {
    this.service = service;
  }

  @GetMapping
  public List<Flight> getAllFlights(@RequestParam(required = false) String flightNo) {
    return service.findFlights(flightNo);
  }

  @GetMapping("/airports")
  public List<Flight> getFlightsByAirports(
      @RequestParam(required = false) String arrival,
      @RequestParam(required = false) String departure) {
    return service.getFlightsByAirport(arrival, departure);
  }

  @GetMapping("/status/{status}")
  public List<Flight> getFlightsByStatus(@PathVariable String status) {
    return service.findFlightsByStatus(status);
  }

  @GetMapping("/departures/{date}")
  public List<Flight> getFlightsByStatus(@PathVariable Date date) {
    return service.findFlightsByDepartureDate(date);
  }

  @GetMapping("/id/{id}")
  public Flight getFlightById(@PathVariable Integer id) {
    return service.getFlightById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Flight saveFlight(@RequestBody @Valid Flight flight) {
    return service.saveNewFlight(flight);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteFlight(@PathVariable Integer id) {
    return service.deleteFlightById(id);
  }

  @PutMapping("/{id}")
  public Flight updateFlight(@RequestBody @Valid Flight newFlight, @PathVariable Integer id) {
    return service.updateFlight(newFlight, id);
  }
}
