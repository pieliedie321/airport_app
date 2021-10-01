package com.app.airport.utils.mapper;

import java.util.List;
import com.app.airport.dto.AircraftDto;
import com.app.airport.dto.SeatDto;
import com.app.airport.entity.Aircraft;
import org.springframework.stereotype.Component;

@Component
public class AircraftsMapper {

  public AircraftDto mapEntityToDto(Aircraft aircraft, List<SeatDto> seats) {
    return AircraftDto.builder()
        .code(aircraft.getCode())
        .model(aircraft.getModel())
        .range(aircraft.getRange())
        .seats(seats)
        .build();
  }
}
