package com.app.airport.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketFlightDto {

  private TicketDto ticket;

  private BoardPassDto boardPass;

  private Integer flightId;

  private String fareConditions;

  private BigDecimal amount;
}
