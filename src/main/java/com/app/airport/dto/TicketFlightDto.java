package com.app.airport.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketFlightDto {

  private List<TicketDto> tickets;

  private List<BoardPassDto> boardPasses;

  private Integer flightId;

  private String fareConditions;

  private BigDecimal amount;
}
