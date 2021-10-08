package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;
import com.app.airport.dto.BookingDto;
import com.app.airport.dto.TicketDto;
import com.app.airport.entity.Ticket;
import com.app.airport.repository.TicketsRepository;
import com.app.airport.utils.mapper.TicketMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for ticktes repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class TicketsService {

  private final TicketsRepository repository;
  private final TicketMapper mapper;
  private final BookingsService bookingsService;

  @Autowired
  public TicketsService(
      TicketsRepository repository, TicketMapper mapper, BookingsService bookingsService) {
    this.repository = repository;
    this.mapper = mapper;
    this.bookingsService = bookingsService;
  }

  public TicketDto findTicket(String ticketNo) {
    return mapTicketDtoFromEntity(
        repository
            .findById(ticketNo)
            .orElseThrow(
                () -> new EntityNotFoundException("Cannot find ticket with id: " + ticketNo)));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void saveNewTicket(TicketDto ticketDto) {
    saveTicket(mapTicketEntityFromDto(ticketDto));
    bookingsService.saveBookings(ticketDto.getBooking());
  }

  private void saveTicket(Ticket ticket) {
    log.debug("Saving new ticket with no: " + ticket.getTicketNo());
    try {
      repository.save(ticket);
    } catch (PersistenceException ex) {
      log.error(
          String.format("Can't save ticket with id: %s, cause: ", ticket.getTicketNo()),
          ex.getCause());
      throw ex;
    }
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteTicket(String id) {
    deleteExistingTicket(id);
    deleteBookings(findBookingDtos(id));
  }

  private void deleteExistingTicket(String id) {
    log.debug("Deleting ticket with no: " + id);
    try {
      repository.deleteById(id);
    } catch (PersistenceException ex) {
      log.error(String.format("Can't save ticket with id: %s, cause: ", id), ex.getCause());
      throw ex;
    }
  }

  private void deleteBookings(List<BookingDto> bookingDtoList) {
    bookingsService.deleteBookings(bookingDtoList);
  }

  private TicketDto mapTicketDtoFromEntity(Ticket ticket) {
    return mapper.mapEntityToDto(ticket, findBookingDtos(ticket.getBookRef()));
  }

  private BookingDto findBookingDto(String bookRef) {
    return bookingsService.findBookingById(bookRef);
  }

  private List<BookingDto> findBookingDtos(String id) {
    return bookingsService.findBookingsById(id);
  }

  private Ticket mapTicketEntityFromDto(TicketDto ticketDto) {
    return mapper.mapDtoToEntity(ticketDto);
  }
}
