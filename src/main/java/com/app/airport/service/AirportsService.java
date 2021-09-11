package com.app.airport.service;

import javax.transaction.Transactional;
import java.util.List;

import com.app.airport.entity.Airport;
import com.app.airport.repository.AirportsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for airports repo. */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class AirportsService {

  private final AirportsRepository repository;

  private final String DELETED = "Airport deleted, with code: ";

  public List<Airport> findAirports(String name) {
    return isNull(name)
        ? repository.findAll()
        : repository.findAirportsByAirportNameContaining(name);
  }

  public List<Airport> findAirportsByCity(String city) {
    return repository.findAirportsByCityContaining(city);
  }

  public List<Airport> findAirportsByTimezone(String timezone) {
    return repository.findAirportsByTimezoneContaining(timezone);
  }

  public Airport findAirportById(String id) {
    return repository.findById(id).orElse(null);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Airport saveNewAirport(Airport airport) {
    log.debug(
        "Saving new airport with code: "
            + airport.getAirportCode()
            + ", and name: "
            + airport.getAirportName());
    return repository.save(airport);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteAircraft(String code) {
    log.debug("Deleting airport with id: " + code);
    repository.deleteById(code);
    return DELETED;
  }
}
