package com.app.airport.utils.mapper;

import com.app.airport.dto.SeatDto;
import com.app.airport.entity.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatsMapper {

  public SeatDto mapEntityToDto(Seat seat) {
    return SeatDto.builder()
        .seatNo(seat.getSeatNo())
        .aircraftCode(seat.getAircraftCode())
        .fareConditions(seat.getFareConditions())
        .build();
  }
}
