package com.app.airport.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AircraftDto {

  @Schema(description = "Identification of aircraft")
  @NotBlank(message = "Aircraft code can't be blank!")
  @NotNull(message = "Aircraft code can't be null!")
  private String code;

  @Schema(description = "Model of aircraft")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String model;

  @Schema(description = "Maximal flight range of aircraft")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer range;

  @Schema(description = "List of aircraft's seats")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<SeatDto> seats;
}
