package com.app.airport.utils.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.app.airport.dto.AirportDto;
import com.app.airport.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportsMapper {

  public AirportDto mapEntityToDto(Airport airport) {
    List<String> coordinates = splitCoordinates(airport.getCoordinates());
    return AirportDto.builder()
        .airportCode(airport.getAirportCode())
        .airportName(airport.getAirportName())
        .city(airport.getCity())
        .longitude(Optional.of(coordinates.get(0)).orElse("Unknown"))
        .latitude(Optional.of(coordinates.get(1)).orElse("Unknown"))
        .timezone(airport.getTimezone())
        .build();
  }

  public Airport mapDtoToEntity(AirportDto airportDto) {
    Airport airport = new Airport();
    airport.setAirportCode(airportDto.getAirportCode());
    airport.setAirportName(airportDto.getAirportName());
    airport.setCity(airportDto.getCity());
    airport.setCoordinates(concatCoordinates(airportDto.getLongitude(), airportDto.getLatitude()));
    airport.setTimezone(airportDto.getTimezone());
    return airport;
  }

  private List<String> splitCoordinates(String coordinates) {
    return Arrays.stream(coordinates.substring(1, coordinates.length() - 1).split(","))
        .map(String::trim)
        .collect(Collectors.toList());
  }

  private String concatCoordinates(String longitude, String latitude) {
    return "(" + longitude + ", " + latitude + ")";
  }
}
