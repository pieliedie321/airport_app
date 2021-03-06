package com.app.airport.service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.app.airport.dto.AirportDto;
import com.app.airport.entity.Airport;
import com.app.airport.repository.AirportsRepository;
import com.app.airport.utils.mapper.AirportsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for airports repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class AirportsService {

  private final AirportsRepository repository;
  private final AirportsMapper mapper;

  @Autowired
  public AirportsService(AirportsRepository repository, AirportsMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public AirportDto findAirportById(String id) {
    return mapAirportDtoFromEntity(
        repository
            .findById(id)
            .orElseThrow(() -> new PersistenceException("Cannot find airport with id: " + id)));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void saveNewAirport(@NotNull AirportDto airportDto) {
    repository.save(mapAirportEntityFromDto(airportDto));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteAircraft(@NotNull @NotBlank String id) {
    repository.deleteById(id);
  }

  private AirportDto mapAirportDtoFromEntity(Airport airport) {
    return mapper.mapEntityToDto(airport);
  }

  private Airport mapAirportEntityFromDto(AirportDto boardPassDto) {
    return mapper.mapDtoToEntity(boardPassDto);
  }
}
