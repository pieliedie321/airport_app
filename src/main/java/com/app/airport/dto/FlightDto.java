package com.app.airport.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightDto {

  private Integer id;

  private String number;

  private List<TicketFlightDto> ticketsFlights;

  private AirportDto departureAirport;

  private AirportDto arrivalAirport;

  private String status;

  private AircraftDto aircraft;

  private Date scheduledDeparture;

  private Date actualDeparture;

  private Date scheduledArrival;

  private Date actualArrival;
}
