package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import com.app.airport.entity.TicketFlight;
import com.app.airport.repository.TicketFlightsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for ticket flights repo. */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class TicketFlightsService {

  private final TicketFlightsRepository repository;

  private final String DELETED = "Ticket flight was deleted, with id: ";

  public List<TicketFlight> findAllTicketFlights() {
    return repository.findAll();
  }

  public TicketFlight findTicketFlightByTicketNo(String ticketNo) {
    return repository.findById(ticketNo).orElse(null);
  }

  public List<TicketFlight> findAllByFlightId(Integer id) {
    return repository.findTicketFlightsByFlightId(id);
  }

  public List<TicketFlight> findAllByFareConditions(String condition) {
    return repository.findTicketFlightsByFareConditions(condition);
  }

  public List<TicketFlight> findAllByAmount(BigDecimal amount) {
    return repository.findTicketFlightsByAmount(amount);
  }

  public List<TicketFlight> findAllByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
    return repository.findTicketFlightsByAmountBetween(minAmount, maxAmount);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public TicketFlight saveNewTicketFlight(TicketFlight ticketFlight) {
    log.debug("Saving new ticket flight with no: " + ticketFlight.getTicketNo());
    return repository.save(ticketFlight);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteTicketFLightById(String id) {
    log.debug("Deleting ticket flight with no: " + id);
    repository.deleteById(id);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public TicketFlight updateTicketFlight(TicketFlight newTicketFlight, String id) {
    return repository
        .findById(id)
        .map(
            ticketFlight -> {
              ticketFlight.setTicketNo(id);
              ticketFlight.setFlightId(newTicketFlight.getFlightId());
              ticketFlight.setFareConditions(newTicketFlight.getFareConditions());
              ticketFlight.setAmount(newTicketFlight.getAmount());
              return repository.save(ticketFlight);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"TicketFlight\" to update, with id: " + id));
  }
}
