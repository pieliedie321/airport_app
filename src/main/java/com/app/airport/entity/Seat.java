package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "seats")
@NoArgsConstructor
public class Seat {

  @Id
  @Column(name = "seat_no")
  private String seatNo;

  @Column(name = "aircraft_code", columnDefinition = "bpchar(3)")
  private String aircraftCode;

  @Column(name = "fare_conditions")
  private String fareConditions;
}
