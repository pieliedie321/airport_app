package com.app.airport.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardPassDto {

  private String ticketNo;

  private Integer flightId;

  private Integer boardingNo;

  private String seatNo;
}
