package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.app.airport.dto.AircraftDto;
import com.app.airport.dto.SeatDto;
import com.app.airport.entity.Aircraft;
import com.app.airport.repository.AircraftsRepository;
import com.app.airport.utils.mapper.AircraftsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for aircrafts repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class AircraftsService {

  private final AircraftsRepository repository;
  private final AircraftsMapper mapper;
  private final SeatsService seatsService;

  @Autowired
  public AircraftsService(
      AircraftsRepository repository, AircraftsMapper mapper, SeatsService seatsService) {
    this.repository = repository;
    this.mapper = mapper;
    this.seatsService = seatsService;
  }

  public AircraftDto findAircraftById(String id) {
    return mapAirportDtoFromEntity(
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cannot find aircraft with id: " + id)));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void saveNewAircraft(AircraftDto aircraftDto) {
    log.debug("Saving new aircraft with code: " + aircraftDto.getCode());
    saveAircraft(mapAircraftEntityFromDto(aircraftDto));
    saveSeats(aircraftDto.getSeats());
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteAircraft(String code) {
    log.debug("Deleting aircraft with id: " + code);
    deleteEntity(code);
  }

  private void deleteEntity(String code) {
    try {
      repository.deleteById(code);
      seatsService.deleteAircraftSeats(code);
    } catch (PersistenceException ex) {
      log.error(
          String.format("Can't delete aircraft with code: %s, cause: ", code) + ex.getCause());
      throw ex;
    }
  }

  private void saveAircraft(Aircraft aircraft) {
    try {
      repository.save(aircraft);
    } catch (PersistenceException ex) {
      log.error(
          String.format("Can't save aircraft with code: %s, cause: ", aircraft.getCode())
              + ex.getCause());
      throw ex;
    }
  }

  private void saveSeats(@NotEmpty @NotNull List<SeatDto> seatDtos) {
    seatsService.saveSeats(seatDtos);
  }

  private AircraftDto mapAirportDtoFromEntity(Aircraft aircraft) {
    return mapper.mapEntityToDto(aircraft, getSeatDtos(aircraft.getCode()));
  }

  private Aircraft mapAircraftEntityFromDto(AircraftDto aircraftDto) {
    return mapper.mapDtoToEntity(aircraftDto);
  }

  private List<SeatDto> getSeatDtos(String aircraftCode) {
    try {
      return seatsService.findSeatsByAircraftCode(aircraftCode);
    } catch (PersistenceException ex) {
      log.error(
          String.format("Can't delete aircraft with code: %s, cause: ", aircraftCode)
              + ex.getCause());
      throw ex;
    }
  }
}
