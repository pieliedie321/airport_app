package com.app.airport.service;

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

  private final TicketFlightsRepository ticketFlightsRepository;

  private final String DELETED = "Ticket flight was deleted, with id: ";

  public List<TicketFlight> findAllTicketFlights() {
    return ticketFlightsRepository.findAll();
  }

  public TicketFlight findTicketFlightByTicketNo(String ticketNo) {
    return ticketFlightsRepository.findById(ticketNo).orElse(null);
  }

  public List<TicketFlight> findAllByFlightId(Integer id) {
    return ticketFlightsRepository.findTicketFlightsByFlightId(id);
  }

  public List<TicketFlight> findAllByFareConditions(String condition) {
    return ticketFlightsRepository.findTicketFlightsByFareConditions(condition);
  }

  public List<TicketFlight> findAllByAmount(BigDecimal amount) {
    return ticketFlightsRepository.findTicketFlightsByAmount(amount);
  }

  public List<TicketFlight> findAllByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
    return ticketFlightsRepository.findTicketFlightsByAmountBetween(minAmount, maxAmount);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public TicketFlight saveNewTicketFlight(TicketFlight ticketFlight) {
    log.debug("Saving new ticket flight with no: " + ticketFlight.getTicketNo());
    return ticketFlightsRepository.save(ticketFlight);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteTicketFLightById(String id) {
    log.debug("Deleting ticket flight with no: " + id);
    ticketFlightsRepository.deleteById(id);
    return DELETED;
  }
}
