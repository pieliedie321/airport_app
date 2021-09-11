package com.app.airport.service;

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

  private final SeatsRepository seatsRepository;

  private final String DELETED = "Seat was deleted, with id: ";

  public List<Seat> findAllSeats() {
    return seatsRepository.findAll();
  }

  public Seat findSeatById(String id) {
    return seatsRepository.findById(id).orElse(null);
  }

  public List<Seat> findSeatsByAircraftCode(String code) {
    return seatsRepository.findSeatsByAircraftCode(code);
  }

  public List<Seat> findSeatsByFareConditions(String condition) {
    return seatsRepository.findSeatsByFareConditions(condition);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Seat saveNewSeat(Seat seat) {
    log.debug("Saving new seat with no: " + seat.getSeatNo());
    return seatsRepository.save(seat);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteSeatById(String id) {
    log.debug("Deleting seat with no: " + id);
    seatsRepository.deleteById(id);
    return DELETED;
  }
}
