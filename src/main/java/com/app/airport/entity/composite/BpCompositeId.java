package com.app.airport.entity.composite;

import javax.persistence.Id;
import java.io.Serializable;

import lombok.Data;

@Data
public class BpCompositeId implements Serializable {

  @Id
  private String ticketNo;

  @Id
  private Integer flightId;
}
