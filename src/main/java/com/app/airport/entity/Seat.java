package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "seats")
public class Seat {

  @Id
  @Column(name = "seat_no")
  private String seatNo;

  @Column(name = "aircraft_code", columnDefinition = "bpchar(3)")
  private String aircraftCode;

  @Column(name = "fare_conditions")
  private String fareConditions;
}
