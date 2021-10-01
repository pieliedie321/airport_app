package com.app.airport.controller;

import java.util.List;
import com.app.airport.dto.FlightDto;
import com.app.airport.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main/flights")
public class MainController {

  private final FlightsService service;

  @Autowired
  public MainController(FlightsService service) {
    this.service = service;
  }

  @GetMapping
  public List<FlightDto> getFlights() {
    return service.constructFlightDtoFromEntities();
  }
}
