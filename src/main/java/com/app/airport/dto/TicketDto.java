package com.app.airport.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketDto {

  private List<BookingDto> bookings;

  private String ticketNo;

  private String bookRef;

  private String passengerId;

  private String passengerName;

  private String contactData;
}
