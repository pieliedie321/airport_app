package com.app.airport.controller;

import javax.validation.Valid;
import java.util.List;
import com.app.airport.dto.AircraftDto;
import com.app.airport.entity.Aircraft;
import com.app.airport.service.AircraftsService;
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

/** REST controller for aircrafts repo. */
@Slf4j
@RestController
@RequestMapping(value = "/aircrafts", produces = "application/json")
public class AircraftsController {

  private final AircraftsService service;

  @Autowired
  public AircraftsController(AircraftsService service) {
    this.service = service;
  }

  @Operation(summary = "Find all aircrafts")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns list of aircrafts",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AircraftDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Aircrafts not found",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping
  public List<AircraftDto> getAllAircrafts(@RequestParam(required = false) String model) {
    return service.findAircrafts(model);
  }

  @Operation(summary = "Find aircrafts with flight range less than in request")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Returns list of aircrafts",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          schema = @Schema(implementation = AircraftDto.class))
                          }),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Aircrafts not found",
                          content = @Content(mediaType = "application/json"))
          })
  @GetMapping("/range/less/{range}")
  public List<AircraftDto> getAircraftsWithRangeLess(@PathVariable Integer range) {
    return service.findAircraftsByRangeLessThan(range);
  }

  @Operation(summary = "Find aircrafts with flight range greater than in request")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Returns list of aircrafts",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          schema = @Schema(implementation = AircraftDto.class))
                          }),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Aircrafts not found",
                          content = @Content(mediaType = "application/json"))
          })
  @GetMapping("/range/greater/{range}")
  public List<AircraftDto> getAircraftsWithRangeGreater(@PathVariable Integer range) {
    return service.findAircraftsByRangeGreaterThan(range);
  }
  @Operation(summary = "Find aircraft by id")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Returns aircraft",
                          content = {
                                  @Content(
                                          mediaType = "application/json",
                                          schema = @Schema(implementation = AircraftDto.class))
                          }),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Aircrafts not found",
                          content = @Content(mediaType = "application/json"))
          })
  @GetMapping("/id/{id}")
  public AircraftDto getAircraftById(@PathVariable String id) {
    return service.findAircraftById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Aircraft saveAircraft(@RequestBody @Valid Aircraft aircraft) {
    return service.saveNewAircraft(aircraft);
  }

  @DeleteMapping("/{code}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteAircraft(@PathVariable String code) {
    return service.deleteAircraft(code);
  }

  @PutMapping("/{id}")
  public Aircraft updateAircraft(
      @RequestBody @Valid Aircraft newAircraft, @PathVariable String id) {
    return service.updateAircraft(newAircraft, id);
  }
}
