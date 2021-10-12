package com.app.airport.service;

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

  @Autowired
  public SeatsService(SeatsRepository repository, SeatsMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<SeatDto> findSeatsByAircraftCode(String code) {
    return mapSeatDtosFromEntities(repository.findSeatsByAircraftCode(code));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void saveSeats(List<SeatDto> seatDtos) {
    for (SeatDto seatDto : seatDtos) {
      saveNewSeat(mapSeatEntityFromDto(seatDto));
    }
  }

  private void saveNewSeat(Seat seat) {
    repository.save(seat);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteAircraftSeats(String aircraftCode) {
    List<Seat> seatsToDelete = mapSeatEntitiesFromDtos(findSeatsByAircraftCode(aircraftCode));
    for (Seat seat : seatsToDelete) {
      deleteSeatById(seat.getSeatNo());
    }
  }

  private void deleteSeatById(String id) {
    repository.deleteById(id);
  }

  private List<SeatDto> mapSeatDtosFromEntities(List<Seat> seats) {
    return seats.stream().map(mapper::mapEntityToDto).collect(Collectors.toList());
  }

  private List<Seat> mapSeatEntitiesFromDtos(List<SeatDto> seatDtos) {
    return seatDtos.stream().map(mapper::mapDtoToEntity).collect(Collectors.toList());
  }

  private Seat mapSeatEntityFromDto(SeatDto seatDto) {
    return mapper.mapDtoToEntity(seatDto);
  }
}
