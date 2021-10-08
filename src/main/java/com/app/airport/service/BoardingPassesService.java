package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import com.app.airport.dto.BoardPassDto;
import com.app.airport.entity.BoardPass;
import com.app.airport.entity.composite.CompositeId;
import com.app.airport.repository.BoardingPassesRepository;
import com.app.airport.utils.mapper.BoardPassesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for boarding passes repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class BoardingPassesService {

  private final BoardingPassesRepository repository;
  private final BoardPassesMapper mapper;

  @Autowired
  public BoardingPassesService(BoardingPassesRepository repository, BoardPassesMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public BoardPassDto findBoardingPassById(CompositeId compositeIdid) {
    BoardPass boardPass = repository.findById(compositeIdid).orElse(null);
    return !isNull(boardPass) ? mapBoardPassDtoToEntity(boardPass) : null;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void saveNewBoardingPass(BoardPassDto boardPassDto) {
    log.debug(
        String.format(
            "Deleting boardingPass with id: : ticketNo - %s, flightId - %s",
            boardPassDto.getTicketNo(), boardPassDto.getFlightId()));
    repository.save(mapBoardPassEntityFromDto(boardPassDto));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteBoardingPass(CompositeId id) {
    log.debug(
        String.format(
            "Deleting boardingPass with id: : ticketNo - %s, flightId - %s",
            id.getTicketNo(), id.getFlightId()));
    repository.deleteById(id);
  }

  private BoardPassDto mapBoardPassDtoToEntity(BoardPass boardPass) {
    return mapper.mapEntityToDto(boardPass);
  }

  private BoardPass mapBoardPassEntityFromDto(BoardPassDto boardPassDto) {
    return mapper.mapDtoToEntity(boardPassDto);
  }
}
