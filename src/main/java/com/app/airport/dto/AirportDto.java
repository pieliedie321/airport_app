package com.app.airport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AirportDto {

  @Schema(description = "Identification code of airport")
  @NotBlank(message = "Airport code cannot be blank!")
  @NotNull(message = "Airport code cannot be null!")
  private String airportCode;

  @Schema(description = "Name of airport")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String airportName;

  @Schema(description = "Airport's city")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String city;

  @Schema(description = "Longitude of airport")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String longitude;

  @Schema(description = "Latitude of airport")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String latitude;

  @Schema(description = "Airport's timezone")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String timezone;
}
