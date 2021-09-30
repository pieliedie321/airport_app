package com.app.airport.controller.other;

import javax.validation.Valid;
import java.util.List;
import com.app.airport.entity.BoardPass;
import com.app.airport.entity.composite.CompositeId;
import com.app.airport.service.BoardingPassesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for boarding passes repo. */
@Slf4j
@RestController
@RequestMapping(value = "/bpasses", produces = "application/json")
public class BoardingPassesController {

  private final BoardingPassesService service;

  @Autowired
  public BoardingPassesController(BoardingPassesService service) {
    this.service = service;
  }

  @GetMapping
  public List<BoardPass> getAllBookings() {
    return service.findAllBoardingPasses();
  }

  @GetMapping("/id/{compositeId}")
  public BoardPass getBookingById(@RequestBody CompositeId compositeId) {
    return service.findBoardingPassById(compositeId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BoardPass saveBooking(@RequestBody @Valid BoardPass boardPass) {
    return service.saveNewBoardingPass(boardPass);
  }

  @DeleteMapping("/id")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteFlight(@RequestBody CompositeId compositeId) {
    return service.deleteBoardingPass(compositeId);
  }

  @PutMapping("/id")
  public BoardPass updateBoardPass(
      @RequestBody @Valid BoardPass newBoardPass, @RequestBody CompositeId compositeId) {
    return service.updateBoardPass(newBoardPass, compositeId);
  }
}
