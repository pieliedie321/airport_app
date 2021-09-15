package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import com.app.airport.entity.Ticket;
import com.app.airport.repository.TicketsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for ticktes repo. */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class TicketsService {

  private final TicketsRepository repository;

  private final String DELETED = "Ticket was deleted, with id: ";

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
}
