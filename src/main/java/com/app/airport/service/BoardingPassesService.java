package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import com.app.airport.dto.BoardPassDto;
import com.app.airport.entity.BoardPass;
import com.app.airport.entity.composite.CompositeId;
import com.app.airport.repository.BoardingPassesRepository;
import com.app.airport.utils.mapper.BoardPassesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for boarding passes repo. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class BoardingPassesService {

  private final BoardingPassesRepository repository;
  private final BoardPassesMapper mapper;
  private final String DELETED = "Board pass deleted, for ticketNo: ";

  @Autowired
  public BoardingPassesService(BoardingPassesRepository repository, BoardPassesMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<BoardPass> findAllBoardingPasses() {
    return repository.findAll();
  }

  public BoardPass findBoardingPassById(CompositeId compositeIdid) {
    return repository.getById(compositeIdid);
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
            "Deleting booking with id: : ticketNo - %s, flightId - %s",
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

  public BoardPassDto constructBoardPassDtoFromEntity(CompositeId id) {
    return mapper.mapEntityToDto(findBoardingPassById(id));
  }
}
