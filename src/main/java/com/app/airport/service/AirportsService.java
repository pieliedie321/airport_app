package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import com.app.airport.dto.AirportDto;
import com.app.airport.entity.Airport;
import com.app.airport.repository.AirportsRepository;
import com.app.airport.utils.mapper.AirportsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for airports repo and mapping. */
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

  public List<AirportDto> findAirports(String name) {
    return isNull(name)
        ? mapAirportDtosFromEntities(repository.findAll())
        : mapAirportDtosFromEntities(repository.findAirportsByAirportNameContaining(name));
  }

  public List<AirportDto> findAirportsByCity(String city) {
    return mapAirportDtosFromEntities(repository.findAirportsByCityContaining(city));
  }

  public List<AirportDto> findAirportsByTimezone(String timezone) {
    return mapAirportDtosFromEntities(repository.findAirportsByTimezoneContaining(timezone));
  }

  public AirportDto findAirportById(String id) {
    return mapAirportDtoFromEntity(
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cannot find airport with id: " + id)));
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
              //TODO переделать данную логику!
              //              airport.setCoordinates("(" + newAirport.getLongitude() + ", " +
              // newAirport.getLatitude() + ")");
              airport.setCoordinates(newAirport.getCoordinates());
              airport.setTimezone(newAirport.getTimezone());
              return repository.save(airport);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"Airport\" to update, with id: " + id));
  }

  private List<AirportDto> mapAirportDtosFromEntities(List<Airport> airports) {
    return airports.stream()
        .map(mapper::mapEntityToDto)
        .collect(Collectors.toList());
  }

  private AirportDto mapAirportDtoFromEntity(Airport airport) {
    return mapper.mapEntityToDto(airport);
  }
}
