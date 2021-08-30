package com.app.aiport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

import lombok.Data;

@Entity
@Data
@Table(name = "flights")
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "flight_id")
  private Integer flightId;

  @Column(name = "flight_no", columnDefinition = "bpchar(10)")
  @NotBlank(message = "Field flightNo can't be null")
  private String flightNo;

  @Column(name = "scheduled_departure")
  private Date scheduledDeparture;

  @Column(name = "scheduled_arrival")
  private Date scheduledArrival;

  @Column(name = "departure_airport", columnDefinition = "bpchar(3)")
  private String departureAirport;

  @Column(name = "arrival_airport", columnDefinition = "bpchar(3)")
  private String arrivalAirport;

  @Column(name = "status")
  private String status;

  @Column(name = "aircraft_code", columnDefinition = "bpchar(3)")
  private String aircraftCode;

  @Column(name = "actual_departure")
  private Date actualDeparture;

  @Column(name = "actual_arrival")
  private Date actualArrival;
}
