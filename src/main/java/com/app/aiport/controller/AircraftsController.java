package com.app.aiport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.aiport.entity.Aircraft;
import com.app.aiport.service.AircraftsService;
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
 * REST контроллер для взаимодействия с aircrafts репо.
 */
@Slf4j
@RestController
@RequestMapping("/aircrafts")
@AllArgsConstructor
public class AircraftsController {

  private final AircraftsService aircraftservice;

  @GetMapping
  public List<Aircraft> getAllAircrafts(@RequestParam(required = false) String model) {
    return aircraftservice.getAircrafts(model);
  }

  @GetMapping("/range/less/{range}")
  public List<Aircraft> getAircraftsWithRangeLess(@PathVariable Integer range) {
    return aircraftservice.getAircraftsByRangeLessThan(range);
  }

  @GetMapping("/range/greater/{range}")
  public List<Aircraft> getAircraftsWithRangeGreater(@PathVariable Integer range) {
    return aircraftservice.getAircraftsByRangeGreaterThan(range);
  }

  @GetMapping("/{id}")
  public Aircraft getAircraftById(@PathVariable String id) {
    return aircraftservice.getAircraftById(id);
  }

  @PostMapping
  public ResponseEntity<Aircraft> saveAircraft(@RequestBody @Valid Aircraft aircraft) {
    return ResponseEntity.ok(aircraftservice.saveNewAircraft(aircraft));
  }

  @DeleteMapping("/{code}")
  public ResponseEntity<String> deleteAircraft(@PathVariable String code) {
    return ResponseEntity.ok(aircraftservice.deleteAircraftByCode(code));
  }
}
