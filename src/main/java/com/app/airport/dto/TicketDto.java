package com.app.airport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class TicketDto {

  @Schema(name = "Booking", description = "Booking flight")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<BookingDto> booking;

  @Schema(description = "Ticket number")
  @NotBlank(message = "Flight number can't be blank!")
  @NotNull(message = "Flight number can't be null!")
  private String ticketNo;

  @Schema(description = "Booking number")
  @NotBlank(message = "Booking number can't be blank!")
  @NotNull(message = "Booking number can't be null!")
  private String bookRef;

  @Schema(description = "Passenger identification")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String passengerId;

  @Schema(description = "Passenger full name")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String passengerName;

  @Schema(description = "Passenger's phone number")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String phoneNumber;

  @Schema(description = "Passenger's email")
  @Email
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String email;
}
