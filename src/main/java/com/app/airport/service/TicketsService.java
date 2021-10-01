package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import com.app.airport.dto.BookingDto;
import com.app.airport.dto.TicketDto;
import com.app.airport.entity.Ticket;
import com.app.airport.repository.TicketsRepository;
import com.app.airport.utils.mapper.TicketsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for ticktes repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class TicketsService {

  private final TicketsRepository repository;
  private final TicketsMapper mapper;
  private final BookingsService bookingsService;
  private final String DELETED = "Ticket was deleted, with id: ";

  @Autowired
  public TicketsService(
      TicketsRepository repository,
      TicketsMapper mapper,
      BookingsService bookingsService) {
    this.repository = repository;
    this.mapper = mapper;
    this.bookingsService = bookingsService;
  }

  public List<TicketDto> findAllTickets() {
    return mapTicketDtosFromEntities(repository.findAll());
  }

  public List<TicketDto> findTicketsByBookRef(String bookRef) {
    return mapTicketDtosFromEntities(repository.findTicketsByBookRef(bookRef));
  }

  public List<TicketDto> findTicketsByPassengerId(String passengerId) {
    return mapTicketDtosFromEntities(repository.findTicketsByPassengerId(passengerId));
  }

  public List<TicketDto> findTicketsByPassengerName(String passengerName) {
    return mapTicketDtosFromEntities(repository.findTicketsByPassengerName(passengerName));
  }

  public TicketDto findTicket(String ticketNo) {
    return mapTicketDtoFromEntity(
        repository
            .findById(ticketNo)
            .orElseThrow(
                () -> new EntityNotFoundException("Cannot find ticket with id: " + ticketNo)));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Ticket saveNewTicket(Ticket ticket) {
    log.debug("Saving new ticket with no: " + ticket.getTicketNo());
    return repository.save(ticket);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteTicket(String id) {
    log.debug("Deleting ticket with no: " + id);
    repository.deleteById(id);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Ticket updateTicket(Ticket newTicket, String id) {
    return repository
        .findById(id)
        .map(
            ticket -> {
              ticket.setTicketNo(id);
              ticket.setBookRef(newTicket.getBookRef());
              ticket.setPassengerId(newTicket.getPassengerId());
              ticket.setPassengerName(newTicket.getPassengerName());
              ticket.setContactData(newTicket.getContactData());
              return repository.save(ticket);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"Ticket\" to update, with id: " + id));
  }

  private List<TicketDto> mapTicketDtosFromEntities(List<Ticket> tickets) {
    return tickets.stream()
        .map(ticket -> mapper.mapEntityToDto(ticket, getBookingDto(ticket.getBookRef())))
        .collect(Collectors.toList());
  }

  private TicketDto mapTicketDtoFromEntity(Ticket ticket) {
    return mapper.mapEntityToDto(ticket, getBookingDto(ticket.getBookRef()));
  }

  private BookingDto getBookingDto(String bookRef) {
    return bookingsService.findBookingById(bookRef);
  }
}
