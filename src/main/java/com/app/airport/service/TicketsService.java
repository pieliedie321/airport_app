package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import com.app.airport.dto.BookingDto;
import com.app.airport.dto.TicketDto;
import com.app.airport.entity.Ticket;
import com.app.airport.repository.TicketsRepository;
import com.app.airport.utils.mapper.TicketsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for ticktes repo. */
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
      TicketsRepository repository, TicketsMapper mapper, BookingsService bookingsService) {
    this.repository = repository;
    this.mapper = mapper;
    this.bookingsService = bookingsService;
  }

  public List<Ticket> findAllTickets() {
    return repository.findAll();
  }

  public List<Ticket> findTicketsByBookRef(String bookRef) {
    return repository.findTicketsByBookRef(bookRef);
  }

  public List<Ticket> findTicketsByPassengerId(String passengerId) {
    return repository.findTicketsByPassengerId(passengerId);
  }

  public List<Ticket> findTicketsByPassengerName(String passengerName) {
    return repository.findTicketsByPassengerName(passengerName);
  }

  public Ticket findTicket(String ticketNo) {
    return repository.findById(ticketNo).orElse(null);
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

  public TicketDto constructTicketDtoFromEntity(String ticketNo) {
    Ticket ticketEntity = findTicket(ticketNo);
    return mapper.mapEntityToDto(ticketEntity, getBookingDto(ticketEntity.getBookRef()));
  }

  private BookingDto getBookingDto(String bookRef) {
    return bookingsService.constructBookingDtoFromEntity(bookRef);
  }
}
