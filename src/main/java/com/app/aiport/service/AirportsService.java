package com.app.aiport.service;

import javax.transaction.Transactional;
import java.util.List;

import com.app.aiport.entity.Airport;
import com.app.aiport.repository.AirportsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/**
 * Сервис доступа к airports репо.
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class AirportsService {

  private final AirportsRepository airportsRepository;

  private final String DELETED = "Airport deleted, with code: ";

  public List<Airport> getAirports(String name) {
    return isNull(name) ? airportsRepository.findAll() :
            airportsRepository.findAirportsByAirportNameContaining(name);
  }

  public List<Airport> getAirportsByCity(String city) {
    return airportsRepository.findAirportsByCityContaining(city);
  }

  public List<Airport> getAirportsByTimezone(String timezone) {
    return airportsRepository.findAirportsByTimezoneContaining(timezone);
  }

  public Airport getAirportById(String id) {
    return airportsRepository.getById(id);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Airport saveNewAirport(Airport airport) {
    log.debug("Saving new airport with code: " + airport.getAirportCode()
            + ", and name: " + airport.getAirportName());
    return airportsRepository.save(airport);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteAircraftByCode(String code) {
    log.debug("Deleting airport with id: " + code);
    airportsRepository.deleteById(code);
    return DELETED;
  }
}
