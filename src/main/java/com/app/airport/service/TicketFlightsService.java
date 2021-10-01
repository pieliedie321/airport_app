package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import com.app.airport.dto.BoardPassDto;
import com.app.airport.dto.TicketDto;
import com.app.airport.dto.TicketFlightDto;
import com.app.airport.entity.TicketFlight;
import com.app.airport.entity.composite.CompositeId;
import com.app.airport.repository.TicketFlightsRepository;
import com.app.airport.utils.mapper.TicketFlightsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for ticket flights repo. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class TicketFlightsService {

  private final TicketFlightsRepository repository;
  private final TicketFlightsMapper mapper;
  private final BoardingPassesService boardingPassesService;
  private final TicketsService ticketsService;
  private final String DELETED = "Ticket flight was deleted, with id: ";

  @Autowired
  public TicketFlightsService(
      TicketFlightsRepository repository,
      TicketFlightsMapper mapper,
      BoardingPassesService boardingPassesService,
      TicketsService ticketsService) {
    this.repository = repository;
    this.mapper = mapper;
    this.boardingPassesService = boardingPassesService;
    this.ticketsService = ticketsService;
  }

  public List<TicketFlight> findAllTicketFlights() {
    return repository.findAll();
  }

  public TicketFlight findTicketFlightById(CompositeId id) {
    return repository.findById(id).orElse(null);
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
  public String deleteTicketFLightById(CompositeId id) {
    log.debug(
        String.format(
            "Deleting ticket flight with id: : ticketNo - %s, flightId - %s",
            id.getTicketNo(), id.getFlightId()));
    repository.deleteById(id);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public TicketFlight updateTicketFlight(TicketFlight newTicketFlight, CompositeId id) {
    return repository
        .findById(id)
        .map(
            ticketFlight -> {
              ticketFlight.setTicketNo(id.getTicketNo());
              ticketFlight.setFlightId(id.getFlightId());
              ticketFlight.setFareConditions(newTicketFlight.getFareConditions());
              ticketFlight.setAmount(newTicketFlight.getAmount());
              return repository.save(ticketFlight);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format(
                        "Cannot find entity \"TicketFlight\" to update, with id: ticketNo - %s, flightId - %s",
                        id.getTicketNo(), id.getFlightId())));
  }

  public List<TicketFlightDto> constructTicketFlightDtosFromEntities(Integer flightId) {
    return findAllByFlightId(flightId).stream()
        .map(
            entity ->
                mapper.mapEntityToDto(
                    entity,
                    getTicketDto(entity.getTicketNo()),
                    getBoardPassDto(new CompositeId(entity.getTicketNo(), entity.getFlightId()))))
        .collect(Collectors.toList());
  }

  private TicketDto getTicketDto(String ticketNo) {
    return ticketsService.constructTicketDtoFromEntity(ticketNo);
  }

  private BoardPassDto getBoardPassDto(CompositeId id) {
    return boardingPassesService.constructBoardPassDtoFromEntity(id);
  }
}
