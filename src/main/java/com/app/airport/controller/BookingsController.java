package com.app.airport.controller;

import javax.validation.Valid;
import java.util.List;
import com.app.airport.dto.BookingDto;
import com.app.airport.entity.Booking;
import com.app.airport.service.BookingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for bookings repo. */
@Slf4j
@RestController
@RequestMapping(value = "/bookings", produces = "application/json")
public class BookingsController {

  private final BookingsService service;

  @Autowired
  public BookingsController(BookingsService service) {
    this.service = service;
  }

  @GetMapping
  public List<BookingDto> getAllBookings() {
    return service.findAllBookings();
  }

  @GetMapping("/id/{id}")
  public BookingDto getBookingById(@PathVariable String id) {
    return service.findBookingById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Booking saveBooking(@RequestBody @Valid Booking booking) {
    return service.saveNewBooking(booking);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteFlight(@PathVariable String id) {
    return service.deleteBooking(id);
  }

  @PutMapping("/{id}")
  public Booking updateBooking(@RequestBody @Valid Booking newBooking, @PathVariable String id) {
    return service.updateBooking(newBooking, id);
  }
}
