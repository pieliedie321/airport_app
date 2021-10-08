package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "aircrafts")
@NoArgsConstructor
public class Aircraft {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "aircraft_code", columnDefinition = "bpchar(3)")
  private String code;

  @Column(name = "model")
  private String model;

  @Column(name = "range")
  private Integer range;
}
