package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Data
@Table(name = "ticket_flights")
public class TicketFlight {

  @Id
  @Column(name = "ticket_no", columnDefinition = "bpchar(13)")
  private String ticketNo;

  @Column(name = "flight_id")
  private Integer flightId;

  @Column(name = "fare_conditions")
  private String fareConditions;

  @Column(name = "amount")
  private BigDecimal amount;
}
