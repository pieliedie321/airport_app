package com.app.airport.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirportDto {

  private String airportCode;

  private String airportName;

  private String city;

  private Double longitude;

  private Double latitude;

  private String timezone;
}
