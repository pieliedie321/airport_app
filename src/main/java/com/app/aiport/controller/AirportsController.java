package com.app.aiport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.aiport.entity.Airport;
import com.app.aiport.service.AirportsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST контроллер для взаимодействия с airports репо.
 */
@Slf4j
@RestController
@RequestMapping("/airports")
@AllArgsConstructor
public class AirportsController {

  private final AirportsService airportsService;

  @GetMapping
  public List<Airport> getAllAirports(@RequestParam(required = false) String name) {
    return airportsService.getAirports(name);
  }

  @GetMapping("/{city}")
  public List<Airport> getAirportsByCity(@PathVariable String city) {
    return airportsService.getAirportsByCity(city);
  }

  @GetMapping("/{timezone}")
  public List<Airport> getAirportsByTimezone(@PathVariable String timezone) {
    return airportsService.getAirportsByTimezone(timezone);
  }

  @GetMapping("/{id}")
  public Airport getFlightById(@PathVariable String id) {
    return airportsService.getAirportById(id);
  }

  @PostMapping
  public ResponseEntity<Airport> saveNewAirport(@RequestBody @Valid Airport airport) {
    return ResponseEntity.ok(airportsService.saveNewAirport(airport));
  }

  @DeleteMapping("/{code}")
  public ResponseEntity<String> deleteAirport(@PathVariable String code) {
    return ResponseEntity.ok(airportsService.deleteAircraftByCode(code));
  }
}
