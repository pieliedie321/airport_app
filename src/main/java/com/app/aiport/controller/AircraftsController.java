package com.app.aiport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.aiport.entity.Aircraft;
import com.app.aiport.service.AircraftsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
