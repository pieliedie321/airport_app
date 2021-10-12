package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
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
    saveAircraft(mapAircraftEntityFromDto(aircraftDto));
    saveSeats(aircraftDto.getSeats());
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteAircraft(String code) {
    deleteEntity(code);
  }

  private void deleteEntity(String code) {
    repository.deleteById(code);
    seatsService.deleteAircraftSeats(code);
  }

  private void saveAircraft(Aircraft aircraft) {
    repository.save(aircraft);
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
    return seatsService.findSeatsByAircraftCode(aircraftCode);
  }
}
