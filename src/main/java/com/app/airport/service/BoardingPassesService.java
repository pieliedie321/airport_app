package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import com.app.airport.dto.BoardPassDto;
import com.app.airport.entity.BoardPass;
import com.app.airport.entity.composite.CompositeId;
import com.app.airport.repository.BoardingPassesRepository;
import com.app.airport.utils.mapper.BoardPassesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for boarding passes repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class BoardingPassesService {

  private final BoardingPassesRepository repository;
  private final BoardPassesMapper mapper;
  private final String DELETED = "Board pass deleted, for ticketNo: ";

  @Autowired
  public BoardingPassesService(
      BoardingPassesRepository repository, BoardPassesMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<BoardPassDto> findAllBoardingPasses() {
    return mapBoardPassDtosToEntities(repository.findAll());
  }

  public BoardPassDto findBoardingPassById(CompositeId compositeIdid) {
    return mapBoardPassDtoToEntity(
        repository
            .findById(compositeIdid)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format(
                            "Deleting boardingPass with id: : ticketNo - %s, flightId - %s",
                            compositeIdid.getTicketNo(), compositeIdid.getFlightId()))));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public BoardPass saveNewBoardingPass(BoardPass boardPass) {
    log.debug("Saving new boardPass for ticketNo: " + boardPass.getTicketNo());
    return repository.save(boardPass);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteBoardingPass(CompositeId id) {
    log.debug(
        String.format(
            "Deleting boardingPass with id: : ticketNo - %s, flightId - %s",
            id.getTicketNo(), id.getFlightId()));
    repository.deleteById(id);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public BoardPass updateBoardPass(BoardPass newBoardPass, CompositeId id) {
    return repository
        .findById(id)
        .map(
            boardPass -> {
              boardPass.setTicketNo(id.getTicketNo());
              boardPass.setFlightId(id.getFlightId());
              boardPass.setBoardingNo(newBoardPass.getBoardingNo());
              boardPass.setSeatNo(newBoardPass.getSeatNo());
              return repository.save(boardPass);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format(
                        "Cannot find entity \"BoardingPass\" to update, with id: ticketNo - %s, flightId - %s",
                        id.getTicketNo(), id.getFlightId())));
  }

  private List<BoardPassDto> mapBoardPassDtosToEntities(List<BoardPass> boardPasses) {
    return boardPasses.stream().map(mapper::mapEntityToDto).collect(Collectors.toList());
  }

  private BoardPassDto mapBoardPassDtoToEntity(BoardPass boardPass) {
    return mapper.mapEntityToDto(boardPass);
  }
}
