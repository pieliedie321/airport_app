package com.app.airport.entity.composite;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Composite id class, that used in TicketFlight and BoardingPass as id. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompositeId implements Serializable {

  @NotNull
  @NotBlank
  private String ticketNo;

  @NotNull
  @NotBlank
  private Integer flightId;
}
