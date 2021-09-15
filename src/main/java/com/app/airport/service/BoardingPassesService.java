package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import com.app.airport.entity.BoardPass;
import com.app.airport.repository.BoardingPassesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for boarding passes repo. */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class BoardingPassesService {

  private final BoardingPassesRepository repository;

  private final String DELETED = "Board pass deleted, for ticketNo: ";

  public List<BoardPass> findAllBoardingPasses() {
    return repository.findAll();
  }

  public BoardPass findBoardingPassById(String id) {
    return repository.getById(id);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public BoardPass saveNewBoardingPass(BoardPass boardPass) {
    log.debug("Saving new boardPass for ticketNo: " + boardPass.getTicketNo());
    return repository.save(boardPass);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteBoardingPass(String ticketNo) {
    log.debug("Deleting booking for ticketNo: " + ticketNo);
    repository.deleteById(ticketNo);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public BoardPass updateBoardPass(BoardPass newBoardPass, String id) {
    return repository
        .findById(id)
        .map(
            boardPass -> {
              boardPass.setTicketNo(id);
              boardPass.setFlight(newBoardPass.getFlight());
              boardPass.setBoardingNo(newBoardPass.getBoardingNo());
              boardPass.setSeatNo(newBoardPass.getSeatNo());
              return repository.save(boardPass);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"BoardingPass\" to update, with id: " + id));
  }
}
