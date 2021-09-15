package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import com.app.airport.entity.Seat;
import com.app.airport.repository.SeatsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for seats repo. */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class SeatsService {

  private final SeatsRepository repository;

  private final String DELETED = "Seat was deleted, with id: ";

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
}
