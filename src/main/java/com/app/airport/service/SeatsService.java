package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import com.app.airport.dto.SeatDto;
import com.app.airport.entity.Seat;
import com.app.airport.repository.SeatsRepository;
import com.app.airport.utils.mapper.SeatsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for seats repo. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class SeatsService {

  private final SeatsRepository repository;
  private final SeatsMapper mapper;
  private final String DELETED = "Seat was deleted, with id: ";

  @Autowired
  public SeatsService(SeatsRepository repository, SeatsMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<Seat> findAllSeats() {
    return repository.findAll();
  }

  public Seat findSeatById(String id) {
    return repository.findById(id).orElse(null);
  }

  public List<Seat> findSeatsByAircraftCode(String code) {
    return repository.findSeatsByAircraftCode(code);
  }

  public List<Seat> findSeatsByFareConditions(String condition) {
    return repository.findSeatsByFareConditions(condition);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Seat saveNewSeat(Seat seat) {
    log.debug("Saving new seat with no: " + seat.getSeatNo());
    return repository.save(seat);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteSeatById(String id) {
    log.debug("Deleting seat with no: " + id);
    repository.deleteById(id);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Seat updateSeat(Seat newSeat, String id) {
    return repository
        .findById(id)
        .map(
            seat -> {
              seat.setSeatNo(id);
              seat.setAircraftCode(newSeat.getAircraftCode());
              seat.setFareConditions(newSeat.getFareConditions());
              return repository.save(seat);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"Seat\" to update, with id: " + id));
  }

  public List<SeatDto> constructSeatDtos(String aircraftCode) {
    List<Seat> seatEntities = findSeatsByAircraftCode(aircraftCode);
    return seatEntities.stream().map(mapper::mapEntityToDto).collect(Collectors.toList());
  }
}
