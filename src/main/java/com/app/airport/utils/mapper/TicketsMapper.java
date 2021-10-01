package com.app.airport.utils.mapper;

import com.app.airport.dto.BookingDto;
import com.app.airport.dto.TicketDto;
import com.app.airport.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketsMapper {

  public TicketDto mapEntityToDto(Ticket ticket, BookingDto booking) {
    return TicketDto.builder()
        .ticketNo(ticket.getTicketNo())
        .bookRef(ticket.getBookRef())
        .passengerId(ticket.getPassengerId())
        .passengerName(ticket.getPassengerName())
        .contactData(ticket.getContactData())
        .booking(booking)
        .build();
  }
}
