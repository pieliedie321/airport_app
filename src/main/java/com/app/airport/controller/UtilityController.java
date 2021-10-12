package com.app.airport.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.app.airport.dto.AircraftDto;
import com.app.airport.dto.AirportDto;
import com.app.airport.service.AircraftsService;
import com.app.airport.service.AirportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Secondary controller, specially for aircraft and airport services. */
@Slf4j
@RestController
@RequestMapping(value = "/utils", produces = "application/json")
public class UtilityController {

  private final AircraftsService aircraftsService;
  private final AirportsService airportsService;

  @Autowired
  public UtilityController(AircraftsService aircraftsService, AirportsService airportsService) {
    this.aircraftsService = aircraftsService;
    this.airportsService = airportsService;
  }

  @Operation(summary = "Save new aircraft in database")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Aircraft successfully saved"),
        @ApiResponse(
            responseCode = "400",
            description = "Wrong input params",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Aircraft not saved",
            content = @Content(mediaType = "application/json"))
      })
  @PostMapping("/aircraft")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveAircraft(@RequestBody @Valid AircraftDto aircraftDto) {
    aircraftsService.saveNewAircraft(aircraftDto);
  }

  @Operation(summary = "Delete existing aircraft from database")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Aircraft successfully deleted"),
        @ApiResponse(
            responseCode = "400",
            description = "Wrong input params",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Aircraft not deleted",
            content = @Content(mediaType = "application/json"))
      })
  @DeleteMapping("/aircraft/{code}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAircraft(@NotNull @NotBlank @PathVariable String code) {
    aircraftsService.deleteAircraft(code);
  }

  @Operation(summary = "Save new airport in database")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Airport successfully saved"),
        @ApiResponse(responseCode = "400", description = "Wrong input params"),
        @ApiResponse(
            responseCode = "500",
            description = "Airport not saved",
            content = @Content(mediaType = "application/json"))
      })
  @PostMapping("/airport")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveNewAirport(@RequestBody @Valid AirportDto airportDto) {
    airportsService.saveNewAirport(airportDto);
  }

  @Operation(summary = "Delete existed airport from database")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Airport successfully deleted"),
        @ApiResponse(
            responseCode = "400",
            description = "Wrong input params",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Airport not saved",
            content = @Content(mediaType = "application/json"))
      })
  @DeleteMapping("/airport/{code}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAirport(@NotNull @NotBlank @PathVariable String code) {
    airportsService.deleteAircraft(code);
  }
}
