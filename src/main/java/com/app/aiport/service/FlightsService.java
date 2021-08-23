package com.app.aiport.service;

import com.app.aiport.entity.Flight;
import com.app.aiport.repository.FlightsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class FlightsService {

    private final FlightsRepository flightsRepository;

    private final String DELETED = "Flight was deleted, with id: ";

    public List<Flight> getFlights(String flightNo) {
        return isNull(flightNo) ? flightsRepository.findAll() :
                flightsRepository.findFlightByFlightNoContaining(flightNo);
    }

    public Flight getFlightById(Integer id) {
        return flightsRepository.findById(id).orElse(null);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Flight saveNewFlight(Flight flight) {
        log.debug("Saving new flight with id: " + flight.getFlightId() + ", and flightNo: " + flight.getFlightNo());
        return flightsRepository.save(flight);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public String deleteFlightById(Integer id) {
        log.debug("Deleting flight with id: " + id);
        flightsRepository.deleteById(id);
        return DELETED;
    }
}
