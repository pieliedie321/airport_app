package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import java.io.Serializable;

import com.app.airport.entity.composite.BpCompositeId;
import lombok.Data;

@Entity
@Data
@Table(name = "boarding_passes")
//@IdClass(value = BpCompositeId.class)
public class BoardPass implements Serializable {

  @Id
  private String ticketNo;

  @Id
  private Integer flightId;

  @Column(name = "boarding_no")
  private Integer boardingNo;

  @Column(name = "seat_no")
  private String seatNo;
}
