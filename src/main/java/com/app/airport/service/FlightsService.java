package com.app.airport.service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import com.app.airport.entity.Flight;
import com.app.airport.repository.FlightsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for flights repo. */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class FlightsService {

  private final FlightsRepository repository;

  private final String DELETED = "Flight was deleted, with id: ";

  public List<Flight> findFlights(String flightNo) {
    return isNull(flightNo)
        ? repository.findAll()
        : repository.findFlightByFlightNoContaining(flightNo);
  }

  public List<Flight> findFlightsByStatus(String status) {
    return repository.findFlightsByStatus(status);
  }

  public List<Flight> findFlightsByDepartureDate(Date departureDate) {
    return repository.findFlightsByScheduledDepartureBefore(departureDate);
  }

  public List<Flight> getFlightsByAirport(String arrival, String departure) {
    if (isNull(arrival) && isNull(departure)) {
      throw new IllegalArgumentException("Arrival and departure can't be combined null");
    } else {
      if (isNull(departure)) {
        return repository.findFlightsByArrivalAirport(arrival);
      } else if (isNull(arrival)) {
        return repository.findFlightsByDepartureAirport(departure);
      } else {
        return repository.findFlightsByArrivalAirportAndDepartureAirport(arrival, departure);
      }
    }
  }

  public Flight getFlightById(Integer id) {
    return repository.findById(id).orElse(null);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Flight saveNewFlight(Flight flight) {
    log.debug(
        "Saving new flight with id: "
            + flight.getFlightId()
            + ", and flightNo: "
            + flight.getFlightNo());
    return repository.save(flight);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteFlightById(Integer id) {
    log.debug("Deleting flight with id: " + id);
    repository.deleteById(id);
    return DELETED;
  }
}
