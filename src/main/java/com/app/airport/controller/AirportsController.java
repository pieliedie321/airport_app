package com.app.airport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.airport.dto.AirportDto;
import com.app.airport.entity.Airport;
import com.app.airport.service.AirportsService;
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

  @GetMapping
  public List<AirportDto> getAllAirports(@RequestParam(required = false) String name) {
    return service.findAirports(name);
  }

  @GetMapping("/city/{city}")
  public List<AirportDto> getAirportsByCity(@PathVariable String city) {
    return service.findAirportsByCity(city);
  }

  @GetMapping("/timezone/{timezone}")
  public List<AirportDto> getAirportsByTimezone(@PathVariable String timezone) {
    return service.findAirportsByTimezone(timezone);
  }

  @GetMapping("/id/{id}")
  public AirportDto getFlightById(@PathVariable String id) {
    return service.findAirportById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Airport saveNewAirport(@RequestBody @Valid Airport airport) {
    return service.saveNewAirport(airport);
  }

  @DeleteMapping("/{code}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteAirport(@PathVariable String code) {
    return service.deleteAircraft(code);
  }

  @PutMapping("/{id}")
  public Airport updateAirport(@RequestBody @Valid Airport newAirport, @PathVariable String id) {
    return service.updateAirport(newAirport, id);
  }
}
