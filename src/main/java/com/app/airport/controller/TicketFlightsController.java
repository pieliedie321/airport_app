package com.app.airport.controller;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import com.app.airport.dto.TicketFlightDto;
import com.app.airport.entity.TicketFlight;
import com.app.airport.entity.composite.CompositeId;
import com.app.airport.service.TicketFlightsService;
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

/** REST controller for ticket flights repo. */
@Slf4j
@RestController
@RequestMapping(value = "/tflights", produces = "application/json")
public class TicketFlightsController {

  private final TicketFlightsService service;

  @Autowired
  public TicketFlightsController(TicketFlightsService service) {
    this.service = service;
  }

  @GetMapping
  public List<TicketFlightDto> findAllTicketFlights() {
    return service.findAllTicketFlights();
  }

  @GetMapping("/flights/{id}")
  public List<TicketFlightDto> findAllByFlightId(@PathVariable Integer id) {
    return service.findAllByFlightId(id);
  }

  @GetMapping("/conditions/{condition}")
  public List<TicketFlightDto> findAllByFareConditions(@PathVariable String condition) {
    return service.findAllByFareConditions(condition);
  }

  @GetMapping("/amount/{amount}")
  public List<TicketFlightDto> findAllByAmount(@PathVariable BigDecimal amount) {
    return service.findAllByAmount(amount);
  }

  @GetMapping("/amount_between/")
  public List<TicketFlightDto> findAllByAmountBetween(
      @RequestParam BigDecimal minAmount, @RequestParam BigDecimal maxAmount) {
    return service.findAllByAmountBetween(minAmount, maxAmount);
  }

  @GetMapping("/id")
  public TicketFlightDto findTicketFlightById(@RequestBody @Valid CompositeId id) {
    return service.findTicketFlightById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TicketFlight saveNewTicketFlight(@RequestBody @Valid TicketFlight ticketFlight) {
    return service.saveNewTicketFlight(ticketFlight);
  }

  @DeleteMapping("/id")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteAircraft(@RequestBody @Valid CompositeId id) {
    return service.deleteTicketFLightById(id);
  }

  @PutMapping("/id")
  public TicketFlight updateTicketFlight(
      @RequestBody @Valid TicketFlight newTicketFlight, @RequestBody @Valid CompositeId id) {
    return service.updateTicketFlight(newTicketFlight, id);
  }
}
