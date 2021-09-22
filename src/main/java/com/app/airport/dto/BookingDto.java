package com.app.airport.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDto {

  private String bookingRef;

  private Date bookDate;

  private BigDecimal totalAmount;
}
