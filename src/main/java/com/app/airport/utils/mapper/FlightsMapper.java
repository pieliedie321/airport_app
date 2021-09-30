package com.app.airport.utils.mapper;

import java.util.List;
import com.app.airport.dto.AircraftDto;
import com.app.airport.dto.AirportDto;
import com.app.airport.dto.FlightDto;
import com.app.airport.dto.TicketFlightDto;
import com.app.airport.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightsMapper {

  public FlightDto mapEntityToDto(
      Flight flight,
      List<TicketFlightDto> ticketFlight,
      AircraftDto aircraft,
      AirportDto departureAirport,
      AirportDto arrivalAirport) {
    return FlightDto.builder()
        .id(flight.getFlightId())
        .number(flight.getFlightNo())
        .ticketsFlights(ticketFlight)
        .status(flight.getStatus())
        .aircraft(aircraft)
        .scheduledDeparture(flight.getScheduledDeparture())
        .actualDeparture(flight.getActualDeparture())
        .scheduledArrival(flight.getScheduledArrival())
        .actualArrival(flight.getActualArrival())
        .departureAirport(departureAirport)
        .arrivalAirport(arrivalAirport)
        .build();
  }
}
