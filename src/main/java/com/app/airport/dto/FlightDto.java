package com.app.airport.dto;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class FlightDto {

  @Schema(name = "Flight identification", description = "Flight identification")
  @NotNull(message = "Flight id can't be null!")
  private Integer id;

  @Schema(description = "Flight number")
  @NotBlank(message = "Flight number can't be blank!")
  @NotNull(message = "Flight number can't be null!")
  private String number;

  @Schema(description = "Ticket flights")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<TicketFlightDto> ticketFlights;

  @Schema(description = "Departure airport")
  private AirportDto departureAirport;

  @Schema(description = "Arrival airport")
  private AirportDto arrivalAirport;

  @Schema(description = "Flight status")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String status;

  @Schema(description = "Aircraft")
  private AircraftDto aircraft;

  @Schema(description = "Scheduled departure time")
  @NotBlank(message = "Scheduled departure time can't be blank!")
  @NotNull(message = "Scheduled departure time can't be null!")
  private Date scheduledDeparture;

  @Schema(description = "Scheduled arrival time")
  @NotBlank(message = "Scheduled arrival time can't be blank!")
  @NotNull(message = "Scheduled arrival time can't be null!")
  private Date scheduledArrival;

  @Schema(description = "Actual departure time")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Date actualDeparture;

  @Schema(description = "Actual arrival time")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Date actualArrival;
}
