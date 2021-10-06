package com.app.airport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class BoardPassDto {

  @Schema(description = "Ticket number")
  @NotBlank(message = "Ticket number cannot be blank!")
  @NotNull(message = "Ticket number cannot be null!")
  private String ticketNo;

  @Schema(description = "Flight identification")
  @NotNull(message = "Flight id cannot be null!")
  private Integer flightId;

  @Schema(description = "Boarding number")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer boardingNo;

  @Schema(description = "Seat number")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String seatNo;
}
