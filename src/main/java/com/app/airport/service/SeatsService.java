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

/** Service for seats repo and mapping and mapping. */
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

  public List<SeatDto> findAllSeats() {
    return mapSeatDtosFromEntities(repository.findAll());
  }

  public SeatDto findSeatById(String id) {
    return mapSeatDtoFromEntity(
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cannot find seat with id: " + id)));
  }

  public List<SeatDto> findSeatsByAircraftCode(String code) {
    return mapSeatDtosFromEntities(repository.findSeatsByAircraftCode(code));
  }

  public List<SeatDto> findSeatsByFareConditions(String condition) {
    return mapSeatDtosFromEntities(repository.findSeatsByFareConditions(condition));
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

  private List<SeatDto> mapSeatDtosFromEntities(List<Seat> seats) {
    return seats.stream().map(mapper::mapEntityToDto).collect(Collectors.toList());
  }

  private SeatDto mapSeatDtoFromEntity(Seat seat) {
    return mapper.mapEntityToDto(seat);
  }
}
