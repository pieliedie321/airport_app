package com.app.airport.utils.mapper;

import com.app.airport.dto.BoardPassDto;
import com.app.airport.dto.TicketDto;
import com.app.airport.dto.TicketFlightDto;
import com.app.airport.entity.TicketFlight;
import org.springframework.stereotype.Component;

@Component
public class TicketFlightsMapper {

  public TicketFlightDto mapEntityToDto(
      TicketFlight ticketFlight, TicketDto ticket, BoardPassDto boardPass) {
    return TicketFlightDto.builder()
        .flightId(ticketFlight.getFlightId())
        .fareConditions(ticketFlight.getFareConditions())
        .amount(ticketFlight.getAmount())
        .ticket(ticket)
        .boardPass(boardPass)
        .build();
  }
}
