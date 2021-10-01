package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import com.app.airport.dto.AircraftDto;
import com.app.airport.dto.SeatDto;
import com.app.airport.entity.Aircraft;
import com.app.airport.repository.AircraftsRepository;
import com.app.airport.utils.mapper.AircraftsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for aircrafts repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class AircraftsService {

  private final AircraftsRepository repository;
  private final AircraftsMapper mapper;
  private final SeatsService seatsService;
  private final String DELETED = "Aircraft deleted, with code: ";

  @Autowired
  public AircraftsService(
      AircraftsRepository repository, AircraftsMapper mapper, SeatsService seatsService) {
    this.repository = repository;
    this.mapper = mapper;
    this.seatsService = seatsService;
  }

  public List<AircraftDto> findAircrafts(String model) {
    return isNull(model)
        ? mapAirportDtosFromEntities(repository.findAll())
        : mapAirportDtosFromEntities(repository.findAircraftByModelContaining(model));
  }

  public List<AircraftDto> findAircraftsByRangeGreaterThan(Integer range) {
    return mapAirportDtosFromEntities(repository.findAircraftsByRangeGreaterThan(range));
  }

  public List<AircraftDto> findAircraftsByRangeLessThan(Integer range) {
    return mapAirportDtosFromEntities(repository.findAircraftsByRangeLessThan(range));
  }

  public AircraftDto findAircraftById(String id) {
    return mapAirportDtoFromEntity(
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cannot find aircraft with id: " + id)));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Aircraft saveNewAircraft(Aircraft aircraft) {
    log.debug(
        "Saving new aircraft with code: "
            + aircraft.getCode()
            + ", and model: "
            + aircraft.getModel());
    return repository.save(aircraft);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteAircraft(String code) {
    log.debug("Deleting aircraft with id: " + code);
    repository.deleteById(code);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Aircraft updateAircraft(Aircraft newAircraft, String id) {
    return repository
        .findById(id)
        .map(
            aircraft -> {
              aircraft.setCode(id);
              aircraft.setModel(newAircraft.getModel());
              aircraft.setRange(newAircraft.getRange());
              return repository.save(aircraft);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"Aircraft\" to update, with id: " + id));
  }

  private List<AircraftDto> mapAirportDtosFromEntities(List<Aircraft> aircrafts) {
    return aircrafts.stream()
        .map(aircraft -> mapper.mapEntityToDto(aircraft, getSeatDtos(aircraft.getCode())))
        .collect(Collectors.toList());
  }

  private AircraftDto mapAirportDtoFromEntity(Aircraft aircraft) {
    return mapper.mapEntityToDto(aircraft, getSeatDtos(aircraft.getCode()));
  }

  private List<SeatDto> getSeatDtos(String aircraftCode) {
    return seatsService.findSeatsByAircraftCode(aircraftCode);
  }
}
