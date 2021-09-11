package com.app.airport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.airport.entity.Ticket;
import com.app.airport.entity.TicketFlight;
import com.app.airport.service.TicketsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for ticket tickets repo. */
@Slf4j
@RestController
@RequestMapping(value = "/tickets", produces="application/json")
@AllArgsConstructor
public class TicketsController {

  private TicketsService service;

  @GetMapping
  public List<Ticket> findAllTickets() {
    return service.findAllTickets();
  }

  @GetMapping("/{bookRef}")
  public List<Ticket> findTicketsByBookRef(@PathVariable String bookRef) {
    return service.findTicketsByBookRef(bookRef);
  }

  @GetMapping("/passengers/id/{passengerId}")
  public List<Ticket> findTicketsByPassengerId(@PathVariable String passengerId) {
    return service.findTicketsByPassengerId(passengerId);
  }

  @GetMapping("/passengers/names/{passengerName}")
  public List<Ticket> findTicketsByPassengerName(@PathVariable String passengerName) {
    return service.findTicketsByPassengerName(passengerName);
  }

  @GetMapping("/numbers/{number}")
  public Ticket findTicket(String number) {
    return service.findTicket(number);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Ticket saveNewTicke(@RequestBody @Valid Ticket ticket) {
    return service.saveNewTicket(ticket);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteTicket(@PathVariable String id) {
    return service.deleteTicket(id);
  }
}
