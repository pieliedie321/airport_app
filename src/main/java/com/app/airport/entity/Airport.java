package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "airports")
public class Airport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "airport_code", columnDefinition = "bpchar(10)")
  private String airportCode;

  @Column(name = "airport_name")
  private String airportName;

  @Column(name = "city")
  private String city;

  @Column(name = "coordinates", columnDefinition = "point")
  private String coordinates;

  @Column(name = "timezone")
  private String timezone;
}
