package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import com.app.airport.dto.AircraftDto;
import com.app.airport.dto.SeatDto;
import com.app.airport.entity.Aircraft;
import com.app.airport.repository.AircraftsRepository;
import com.app.airport.utils.mapper.AircraftsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for aircrafts repo. */
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

  public List<Aircraft> findAircrafts(String model) {
    return isNull(model) ? repository.findAll() : repository.findAircraftByModelContaining(model);
  }

  public List<Aircraft> findAircraftsByRangeGreaterThan(Integer range) {
    return repository.findAircraftsByRangeGreaterThan(range);
  }

  public List<Aircraft> findAircraftsByRangeLessThan(Integer range) {
    return repository.findAircraftsByRangeLessThan(range);
  }

  public Aircraft findAircraftById(String id) {
    return repository.findById(id).orElse(null);
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

  public AircraftDto constructAircraftDtoFromEntity(String aircraftCode) {
    Aircraft aircraft =
        repository
            .findById(aircraftCode)
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Can't find aircraft with code: " + aircraftCode));
    return mapper.mapEntityToDto(aircraft, getSeatDtos(aircraftCode));
  }

  public List<SeatDto> getSeatDtos(String aircraftCode) {
    return seatsService.constructSeatDtos(aircraftCode);
  }
}
