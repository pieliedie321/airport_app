package com.app.aiport.service;

import javax.transaction.Transactional;
import java.util.List;

import com.app.aiport.entity.BoardPass;
import com.app.aiport.repository.BoardingPassesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Сервис доступа к boarding passes репо.
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class BoardingPassesService {

  private final BoardingPassesRepository boardingPassesRepository;

  private final String DELETED = "Board pass deleted, for ticketNo: ";

  public List<BoardPass> getAllBoardingPasses() {
    return boardingPassesRepository.findAll();
  }

  public BoardPass getBoardingPassById(String id) {
    return boardingPassesRepository.getById(id);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public BoardPass saveNewBoardingPass(BoardPass boardPass) {
    log.debug("Saving new boardPass for ticketNo: " + boardPass.getTicketNo());
    return boardingPassesRepository.save(boardPass);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteBoardingByTicketNo(String ticketNo) {
    log.debug("Deleting booking for ticketNo: " + ticketNo);
    boardingPassesRepository.deleteById(ticketNo);
    return DELETED;
  }
}
