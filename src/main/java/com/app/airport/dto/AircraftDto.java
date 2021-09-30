package com.app.airport.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AircraftDto {

  private String code;

  private String model;

  private Integer range;

  private List<SeatDto> seats;
}
