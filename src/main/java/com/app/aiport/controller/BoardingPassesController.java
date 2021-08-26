package com.app.aiport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.aiport.entity.BoardPass;
import com.app.aiport.service.BoardingPassesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST контроллер для взаимодействия с boarding passes репо.
 */
@Slf4j
@RestController
@RequestMapping("/bpasses")
@AllArgsConstructor
public class BoardingPassesController {

  private final BoardingPassesService boardingPassesService;

  @GetMapping
  public List<BoardPass> getAllBookings() {
    return boardingPassesService.getAllBoardingPasses();
  }

  @GetMapping("/id/{id}")
  public BoardPass getBookingById(@PathVariable String id) {
    return boardingPassesService.getBoardingPassById(id);
  }

  @PostMapping
  public ResponseEntity<BoardPass> saveBooking(@RequestBody @Valid BoardPass boardPass) {
    return ResponseEntity.ok(boardingPassesService.saveNewBoardingPass(boardPass));
  }

  @DeleteMapping("/{ticketNo}")
  public ResponseEntity<String> deleteFlight(@PathVariable String ticketNo) {
    return ResponseEntity.ok(boardingPassesService.deleteBoardingByTicketNo(ticketNo));
  }
}