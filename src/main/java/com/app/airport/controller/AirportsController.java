package com.app.airport.controller;

import javax.validation.Valid;
import java.util.List;
import com.app.airport.dto.AirportDto;
import com.app.airport.entity.Airport;
import com.app.airport.service.AirportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for airports repo. */
@Slf4j
@RestController
@RequestMapping(value = "/airports", produces = "application/json")
public class AirportsController {

  private final AirportsService service;

  @Autowired
  public AirportsController(AirportsService service) {
    this.service = service;
  }

  @Operation(summary = "Find all airports or airport by its name")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of airports",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AirportDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Airports not found",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping
  public List<AirportDto> getAllAirports(@RequestParam(required = false) String name) {
    return service.findAirports(name);
  }

  @Operation(summary = "Find airports in city")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of airports",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AirportDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Airports not found",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/city/{city}")
  public List<AirportDto> getAirportsByCity(@PathVariable String city) {
    return service.findAirportsByCity(city);
  }

  @Operation(summary = "Find airports in timezone")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of airports",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AirportDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Airports not found",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/timezone/{timezone}")
  public List<AirportDto> getAirportsByTimezone(@PathVariable String timezone) {
    return service.findAirportsByTimezone(timezone);
  }

  @Operation(summary = "Find airport by id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns airport",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AirportDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Airport not found",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/id/{id}")
  public AirportDto getFlightById(@PathVariable String id) {
    return service.findAirportById(id);
  }

  @Operation(summary = "Save new airport in database")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Airport successfully saved",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AirportDto.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Airport not saved",
            content = @Content(mediaType = "application/json"))
      })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Airport saveNewAirport(@RequestBody @Valid Airport airport) {
    return service.saveNewAirport(airport);
  }

  @Operation(summary = "Delete existed airport from database")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Airport successfully deleted"),
        @ApiResponse(responseCode = "500", description = "Airport not saved")
      })
  @DeleteMapping("/{code}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAirport(@PathVariable String code) {
    service.deleteAircraft(code);
  }

  @Operation(summary = "Update existed airport in database")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Airport successfully updated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AirportDto.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Airport not updated",
            content = @Content(mediaType = "application/json"))
      })
  @PutMapping("/{id}")
  public Airport updateAirport(@RequestBody @Valid Airport newAirport, @PathVariable String id) {
    return service.updateAirport(newAirport, id);
  }
}
