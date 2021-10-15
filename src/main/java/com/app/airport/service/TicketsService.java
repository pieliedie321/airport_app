package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
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
    checkAndGeneratePassId(ticketDto);
    saveTicket(mapTicketEntityFromDto(ticketDto));
    bookingsService.saveBookings(ticketDto.getBooking());
  }

  private void saveTicket(Ticket ticket) {
    repository.save(ticket);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteTicket(String id) {
    deleteExistingTicket(id);
    deleteBookings(findBookingDtos(id));
  }

  private void deleteExistingTicket(String id) {
    repository.deleteById(id);
  }

  private void deleteBookings(List<BookingDto> bookingDtoList) {
    bookingsService.deleteBookings(bookingDtoList);
  }

  private TicketDto mapTicketDtoFromEntity(Ticket ticket) {
    return mapper.mapEntityToDto(ticket, findBookingDtos(ticket.getBookRef()));
  }

  private List<BookingDto> findBookingDtos(String id) {
    return bookingsService.findBookingsById(id);
  }

  private Ticket mapTicketEntityFromDto(TicketDto ticketDto) {
    return mapper.mapDtoToEntity(ticketDto);
  }

  private void checkAndGeneratePassId(TicketDto ticketDto) {
    boolean isExist = checkIfPassengerIdIsPresentInRepository(ticketDto.getPassengerId());
    if (isExist) {
      generatePassId(isExist, ticketDto);
    }
  }

  private void generatePassId(boolean isExist, TicketDto ticketDto) {
    String passengerId;
    while (isExist) {
      passengerId = getPassengerId();
      isExist = checkIfPassengerIdIsPresentInRepository(passengerId);
      if (!isExist) {
        ticketDto.setPassengerId(passengerId);
        break;
      }
    }
  }

  private boolean checkIfPassengerIdIsPresentInRepository(String passengerId) {
    return repository.findTicketsByPassengerId(passengerId).stream().findFirst().isPresent();
  }

  private String getPassengerId() {
    return getNumber(4) + " " + getNumber(6);
  }

  /**
   * This method can generate number with 10 chars max (Integer type constraints).
   *
   * @param chars chars in result number
   * @return String value of generated number
   */
  private String getNumber(Integer chars) {
    int rndLimiter = (int) Math.round(Math.pow(10, chars) - 1);
    Random rnd = new Random();
    String number = String.valueOf(rnd.nextInt(rndLimiter));
    if (number.length() < chars) {
      int missingInt = chars - number.length();
      StringBuilder finalNumberBuilder = new StringBuilder(number);
      for (int i = 0; i < missingInt; i++) {
        finalNumberBuilder.insert(0, "0");
        number = finalNumberBuilder.toString();
      }
    }
    return number;
  }
}
