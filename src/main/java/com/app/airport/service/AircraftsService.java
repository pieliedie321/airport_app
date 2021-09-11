package com.app.airport.service;

import javax.transaction.Transactional;
import java.util.List;

import com.app.airport.entity.Aircraft;
import com.app.airport.repository.AircraftsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for aircrafts repo. */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class AircraftsService {

  private final AircraftsRepository repository;

  private final String DELETED = "Aircraft deleted, with code: ";

  public List<Aircraft> findAircrafts(String model) {
    return isNull(model)
        ? repository.findAll()
        : repository.findAircraftByModelContaining(model);
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
}
