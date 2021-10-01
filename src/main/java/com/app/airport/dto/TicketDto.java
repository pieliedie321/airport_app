package com.app.airport.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketDto {

  private BookingDto booking;

  private String ticketNo;

  private String bookRef;

  private String passengerId;

  private String passengerName;

  private String contactData;
}
