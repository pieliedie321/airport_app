package com.app.airport.utils.mapper;

import com.app.airport.dto.BookingDto;
import com.app.airport.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingsMapper {

  public BookingDto mapEntityToDto(Booking booking) {
    return BookingDto.builder()
        .bookingRef(booking.getBookingRef())
        .bookDate(booking.getBookDate())
        .totalAmount(booking.getTotalAmount())
        .build();
  }
}
