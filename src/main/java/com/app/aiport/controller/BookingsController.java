package com.app.aiport.controller;

import javax.validation.Valid;
import java.util.List;

import com.app.aiport.entity.Booking;
import com.app.aiport.service.BookingsService;
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

/** REST контроллер для взаимодействия с bookings репо. */
@Slf4j
@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingsController {

  private final BookingsService bookingsService;

  @GetMapping
  public List<Booking> getAllBookings() {
    return bookingsService.getAllBookings();
  }

  @GetMapping("/id/{id}")
  public Booking getBookingById(@PathVariable String id) {
    return bookingsService.getBookingById(id);
  }

  @PostMapping
  public ResponseEntity<Booking> saveBooking(@RequestBody @Valid Booking booking) {
    return ResponseEntity.ok(bookingsService.saveNewBooking(booking));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteFlight(@PathVariable String id) {
    return ResponseEntity.ok(bookingsService.deleteBookingsByCode(id));
  }
}
