package com.app.airport.service;

import java.util.List;
import javax.transaction.Transactional;
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

  private final TicketsRepository ticketsRepository;

  private final String DELETED = "Ticket was deleted, with id: ";

  public List<Ticket> findAllTickets() {
    return ticketsRepository.findAll();
  }

  public List<Ticket> findTicketsByBookRef(String bookRef) {
    return ticketsRepository.findTicketsByBookRef(bookRef);
  }

  public List<Ticket> findTicketsByPassengerId(String passengerId) {
    return ticketsRepository.findTicketsByPassengerId(passengerId);
  }

  public List<Ticket> findTicketsByPassengerName(String passengerName) {
    return ticketsRepository.findTicketsByPassengerName(passengerName);
  }

  public Ticket findTicket(String ticketNo) {
    return ticketsRepository.findById(ticketNo).orElse(null);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Ticket saveNewTicket(Ticket ticket) {
    log.debug("Saving new ticket with no: " + ticket.getTicketNo());
    return ticketsRepository.save(ticket);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteTicket(String id) {
    log.debug("Deleting ticket with no: " + id);
    ticketsRepository.deleteById(id);
    return DELETED;
  }
}
