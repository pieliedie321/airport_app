package com.app.airport.controller;

import java.util.List;
import javax.validation.Valid;
import com.app.airport.entity.Aircraft;
import com.app.airport.service.AircraftsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/** REST controller for aircrafts repo. */
@Slf4j
@RestController
@RequestMapping(value = "/aircrafts", produces="application/json")
@AllArgsConstructor
public class AircraftsController {

  private final AircraftsService service;

  @GetMapping
  public List<Aircraft> getAllAircrafts(@RequestParam(required = false) String model) {
    return service.findAircrafts(model);
  }

  @GetMapping("/range/less/{range}")
  public List<Aircraft> getAircraftsWithRangeLess(@PathVariable Integer range) {
    return service.findAircraftsByRangeLessThan(range);
  }

  @GetMapping("/range/greater/{range}")
  public List<Aircraft> getAircraftsWithRangeGreater(@PathVariable Integer range) {
    return service.findAircraftsByRangeGreaterThan(range);
  }

  @GetMapping("/id/{id}")
  public Aircraft getAircraftById(@PathVariable String id) {
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
}
