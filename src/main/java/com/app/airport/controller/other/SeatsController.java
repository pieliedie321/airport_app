package com.app.airport.controller.other;

import javax.validation.Valid;
import java.util.List;
import com.app.airport.entity.Seat;
import com.app.airport.service.SeatsService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for seats repo. */
@Slf4j
@RestController
@RequestMapping(value = "/seats", produces = "application/json")
public class SeatsController {

  private final SeatsService service;

  @Autowired
  public SeatsController(SeatsService service) {
    this.service = service;
  }

  @GetMapping
  public List<Seat> findAllSeats() {
    return service.findAllSeats();
  }

  @GetMapping("/aircrafts/{code}")
  public List<Seat> findSeatsByAircraftCode(@PathVariable String code) {
    return service.findSeatsByAircraftCode(code);
  }

  @GetMapping("/conditions/{condition}")
  public List<Seat> findSeatsByFareConditions(@PathVariable String condition) {
    return service.findSeatsByFareConditions(condition);
  }

  @GetMapping("/id/{id}")
  public Seat findSeatById(@PathVariable String id) {
    return service.findSeatById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Seat saveNewSeat(@RequestBody @Valid Seat seat) {
    return service.saveNewSeat(seat);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteAircraft(@PathVariable String id) {
    return service.deleteSeatById(id);
  }

  @PutMapping("/{id}")
  public Seat updateSeat(@RequestBody @Valid Seat newSeat, @PathVariable String id) {
    return service.updateSeat(newSeat, id);
  }
}
