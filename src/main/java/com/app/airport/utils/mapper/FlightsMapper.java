package com.app.airport.utils.mapper;

import java.util.List;
import java.util.Optional;

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
        .ticketFlights(ticketFlight)
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

  public Flight mapDtoToEntity(FlightDto flightDto) {
    Flight flight = new Flight();
    flight.setFlightId(flightDto.getId());
    flight.setFlightNo(flightDto.getNumber());
    flight.setScheduledDeparture(flightDto.getScheduledDeparture());
    flight.setScheduledArrival(flightDto.getScheduledArrival());
    flight.setDepartureAirport(flightDto.getDepartureAirport().getAirportName());
    flight.setArrivalAirport(flightDto.getArrivalAirport().getAirportName());
    flight.setStatus(flightDto.getStatus());
    flight.setAircraftCode(flightDto.getAircraft().getCode());
    flight.setActualDeparture(Optional.of(flightDto.getActualDeparture()).orElse(null));
    flight.setActualArrival(Optional.of(flightDto.getActualArrival()).orElse(null));
    return flight;
  }
}
