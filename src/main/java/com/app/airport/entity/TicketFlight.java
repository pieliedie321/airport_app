package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import com.app.airport.entity.composite.CompositeId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ticket_flights")
@IdClass(value = CompositeId.class)
@NoArgsConstructor
public class TicketFlight {

  @Id
  @Column(name = "ticket_no", columnDefinition = "bpchar(13)")
  private String ticketNo;

  @Id
  @Column(name = "flight_id")
  private Integer flightId;

  @Column(name = "fare_conditions")
  private String fareConditions;

  @Column(name = "amount")
  private BigDecimal amount;
}
