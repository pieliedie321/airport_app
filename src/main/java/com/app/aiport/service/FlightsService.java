package com.app.aiport.service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import com.app.aiport.entity.Flight;
import com.app.aiport.repository.FlightsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Сервис доступа к flights репо. */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class FlightsService {

  private final FlightsRepository flightsRepository;

  private final String DELETED = "Flight was deleted, with id: ";

  public List<Flight> getFlights(String flightNo) {
    return isNull(flightNo)
        ? flightsRepository.findAll()
        : flightsRepository.findFlightByFlightNoContaining(flightNo);
  }

  public List<Flight> getFlightsByStatus(String status) {
    return flightsRepository.findFlightsByStatus(status);
  }

  public List<Flight> getFlightsByDepartureDate(Date departureDate) {
    return flightsRepository.findFlightsByScheduledDepartureBefore(departureDate);
  }

  public List<Flight> getFlightsByAirport(String arrival, String departure) {
    if (isNull(arrival) && isNull(departure)) {
      throw new IllegalArgumentException("Arrival and departure can't be combined null");
    } else {
      if (isNull(departure)) {
        return flightsRepository.findFlightsByArrivalAirport(arrival);
      } else if (isNull(arrival)) {
        return flightsRepository.findFlightsByDepartureAirport(departure);
      } else {
        return flightsRepository.findFlightsByArrivalAirportAndDepartureAirport(arrival, departure);
      }
    }
  }

  public Flight getFlightById(Integer id) {
    return flightsRepository.findById(id).orElse(null);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Flight saveNewFlight(Flight flight) {
    log.debug(
        "Saving new flight with id: "
            + flight.getFlightId()
            + ", and flightNo: "
            + flight.getFlightNo());
    return flightsRepository.save(flight);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteFlightById(Integer id) {
    log.debug("Deleting flight with id: " + id);
    flightsRepository.deleteById(id);
    return DELETED;
  }
}
