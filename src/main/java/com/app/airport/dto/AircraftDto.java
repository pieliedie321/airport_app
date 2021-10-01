package com.app.airport.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AircraftDto {

  @Schema(description = "Identification of aircraft")
  private String code;

  @Schema(description = "Model of aircraft")
  private String model;

  @Schema(description = "Maximal flight range of aircraft")
  private Integer range;

  @Schema(description = "List of aircraft's seats")
  private List<SeatDto> seats;
}
