package com.app.airport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Entity
@Data
@Table(name = "bookings")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_ref", columnDefinition = "bpchar(3)")
  private String bookingRef;

  @Column(name = "book_date")
  private Date bookDate;

  @Column(name = "total_amount")
  private BigDecimal totalAmount;
}
