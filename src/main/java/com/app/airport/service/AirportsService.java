package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import com.app.airport.dto.AirportDto;
import com.app.airport.entity.Airport;
import com.app.airport.repository.AirportsRepository;
import com.app.airport.utils.mapper.AirportsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for airports repo. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class AirportsService {

  private final AirportsRepository repository;
  private final AirportsMapper mapper;
  private final String DELETED = "Airport deleted, with code: ";

  @Autowired
  public AirportsService(AirportsRepository repository, AirportsMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

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
  public String deleteAircraft(String id) {
    log.debug("Deleting airport with id: " + id);
    repository.deleteById(id);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Airport updateAirport(Airport newAirport, String id) {
    return repository
        .findById(id)
        .map(
            airport -> {
              airport.setAirportCode(id);
              airport.setAirportName(newAirport.getAirportName());
              airport.setCity(newAirport.getCity());
              airport.setLongitude(newAirport.getLongitude());
              airport.setLatitude(newAirport.getLatitude());
              airport.setTimezone(newAirport.getTimezone());
              return repository.save(airport);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"Airport\" to update, with id: " + id));
  }

  public AirportDto constructAircportDtoFromEntity(String airportCode) {
    return mapper.mapEntityToDto(findAirportById(airportCode));
  }
}
