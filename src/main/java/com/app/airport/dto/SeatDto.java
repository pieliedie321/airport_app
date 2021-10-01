package com.app.airport.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatDto {
  private String seatNo;

  private String aircraftCode;

  private String fareConditions;
}
