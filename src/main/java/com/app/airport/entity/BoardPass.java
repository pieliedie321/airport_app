package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import com.app.airport.entity.composite.CompositeId;
import lombok.Data;

@Entity
@Data
@Table(name = "boarding_passes")
@IdClass(value = CompositeId.class)
public class BoardPass {

  @Id
  @Column(name = "ticket_no", columnDefinition = "bpchar(13)")
  private String ticketNo;

  @Id
  @Column(name = "flight_id")
  private Integer flightId;

  @Column(name = "boarding_no")
  private Integer boardingNo;

  @Column(name = "seat_no")
  private String seatNo;
}
