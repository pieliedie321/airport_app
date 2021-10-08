package com.app.airport.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class TicketFlightDto {

  @Schema(description = "Ticket")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private TicketDto ticket;

  @Schema(description = "Board pass")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private BoardPassDto boardPass;

  @Schema(description = "Flight identification")
  @NotNull(message = "Flight id can't be null!")
  private Integer flightId;

  @Schema(description = "Fare conditions of seat")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String fareConditions;

  @Schema(description = "Amount")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private BigDecimal amount;
}
