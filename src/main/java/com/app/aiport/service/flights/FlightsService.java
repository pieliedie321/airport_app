package com.app.aiport.service.flights;

import com.app.aiport.entity.Flight;
import com.app.aiport.repository.FlightsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
public class FlightsService {

    private final FlightsRepository flightsRepository;

    public List<Flight> getFlights(String flightNo) {
        return isNull(flightNo) ? flightsRepository.findAll() : flightsRepository.findFlightByFlightNoContaining(flightNo);
    }

    public Flight getFlightById(Integer id) {
        return flightsRepository.findById(id).orElse(null);
    }
}
