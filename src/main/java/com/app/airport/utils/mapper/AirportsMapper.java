package com.app.airport.utils.mapper;

import com.app.airport.dto.AirportDto;
import com.app.airport.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportsMapper {

  public AirportDto mapEntityToDto(Airport airport) {
    return AirportDto.builder()
        .airportCode(airport.getAirportCode())
        .airportName(airport.getAirportName())
        .city(airport.getCity())
        .longitude(airport.getLongitude())
        .latitude(airport.getLatitude())
        .timezone(airport.getTimezone())
        .build();
  }
}
