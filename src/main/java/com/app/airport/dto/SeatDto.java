package com.app.airport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class SeatDto {

  @Schema(description = "Seat number")
  @NotBlank(message = "Flight number can't be blank!")
  @NotNull(message = "Flight number can't be null!")
  private String seatNo;

  @Schema(description = "Aircraft code")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String aircraftCode;

  @Schema(description = "Fare conditions of seat")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String fareConditions;
}
