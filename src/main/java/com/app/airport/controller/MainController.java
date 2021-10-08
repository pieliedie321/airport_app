package com.app.airport.controller;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import com.app.airport.dto.FlightDto;
import com.app.airport.service.FlightsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Main controller. */
@Slf4j
@RestController
@RequestMapping(value = "/flights", produces = "application/json")
public class MainController {

  private final FlightsService service;

  @Autowired
  public MainController(FlightsService service) {
    this.service = service;
  }

  @Operation(
      summary = "Find all flights, or find flight by its number",
      parameters = {
        @Parameter(
            in = ParameterIn.PATH,
            name = "flightNo",
            schema = @Schema(type = "String", description = "Flights number"))
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of flights",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FlightDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Flights not found",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping
  public List<FlightDto> getAllFlights(@RequestParam(required = false) String flightNo) {
    return service.findFlights(flightNo);
  }

  @Operation(summary = "Find latest arrival flights")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of flights",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FlightDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Flights not found",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/arrival")
  public List<FlightDto> findLatestArrivalFlights() {
    return service.findLatestArrivalFlights();
  }

  @Operation(summary = "Find latest departure flights")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of flights",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FlightDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Flights not found",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/departure")
  public List<FlightDto> findLatestDepartureFlights() {
    return service.findLatestDepartureFlights();
  }

  @Operation(
      summary = "Find flights with transmitted arrival and/or departure airports",
      parameters = {
        @Parameter(
            in = ParameterIn.PATH,
            name = "arrival",
            schema = @Schema(type = "String", description = "Arrival airport")),
        @Parameter(
            in = ParameterIn.PATH,
            name = "departure",
            schema = @Schema(type = "String", description = "Departure airport"))
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of flights",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FlightDto.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Wrong airport name or both airport names null/blank",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            description = "Flights with transmitted airport name/names not found",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/airports")
  public List<FlightDto> getFlightsByAirports(
      @RequestParam(required = false) String arrival,
      @RequestParam(required = false) String departure) {
    return service.getFlightsByAirport(arrival, departure);
  }

  @Operation(
      summary = "Find flights by status",
      parameters = {
        @Parameter(
            in = ParameterIn.PATH,
            name = "status",
            schema = @Schema(type = "String", required = true, description = "Flight's status"))
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of flights",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FlightDto.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Wrong status",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            description = "Flights with transmitted status not found",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/status/{status}")
  public List<FlightDto> getFlightsByStatus(@PathVariable String status) {
    return service.findFlightsByStatus(status);
  }

  @Operation(
      summary = "Find flights by departure date",
      parameters = {
        @Parameter(
            in = ParameterIn.PATH,
            name = "date",
            schema =
                @Schema(type = "Date", required = true, description = "Flight's departure date"))
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of flights",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FlightDto.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Error in date",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            description = "Flights with transmitted departure date not found",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/departures/{date}")
  public List<FlightDto> getFlightsByStatus(@PathVariable Date date) {
    return service.findFlightsByDepartureDate(date);
  }

  @Operation(
      summary = "Find flight by it's identificator",
      parameters = {
        @Parameter(
            in = ParameterIn.PATH,
            name = "id",
            schema =
                @Schema(type = "integer", required = true, description = "Flight's departure date"))
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of flights",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FlightDto.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Error in id",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            description = "Flights with transmitted id not found",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/id/{id}")
  public FlightDto getFlightById(@PathVariable Integer id) {
    return service.getFlightById(id);
  }

  @Operation(
      summary = "Save new flight",
      parameters = {@Parameter(in = ParameterIn.PATH, name = "flight")})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Return saved flight",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = FlightDto.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Error in flight data object",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void saveFlight(@RequestBody @Valid FlightDto flightDto) {
    service.saveNewFlight(flightDto);
  }

  @Operation(
      summary = "Delete existing flight by it's identificator",
      parameters = {
        @Parameter(
            in = ParameterIn.PATH,
            name = "id",
            schema =
                @Schema(type = "integer", required = true, description = "Flight identificator"))
      })
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Returns list of flights"),
        @ApiResponse(
            responseCode = "400",
            description = "Error in flight data object",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json"))
      })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteFlight(@PathVariable Integer id) {
    service.deleteFlightById(id);
  }
}
