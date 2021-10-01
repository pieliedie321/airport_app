package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import com.app.airport.dto.BookingDto;
import com.app.airport.entity.Booking;
import com.app.airport.repository.BookingsRepository;
import com.app.airport.utils.mapper.BookingsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for bookings repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class BookingsService {

  private final BookingsRepository repository;
  private final BookingsMapper mapper;
  private final String DELETED = "Booking deleted, with code: ";

  @Autowired
  public BookingsService(
      BookingsRepository repository, BookingsMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<BookingDto> findAllBookings() {
    return mapBookingDtosFromEntities(repository.findAll());
  }

  public BookingDto findBookingById(String id) {
    return mapBookingDtoFromEntity(
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cannot find booking with id " + id)));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Booking saveNewBooking(Booking booking) {
    log.debug("Saving new booking with code: " + booking.getBookingRef());
    return repository.save(booking);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteBooking(String code) {
    log.debug("Deleting booking with id: " + code);
    repository.deleteById(code);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Booking updateBooking(Booking newBooking, String id) {
    return repository
        .findById(id)
        .map(
            booking -> {
              booking.setBookingRef(id);
              booking.setBookDate(newBooking.getBookDate());
              booking.setTotalAmount(newBooking.getTotalAmount());
              return repository.save(booking);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"Airport\" to update, with id: " + id));
  }

  private List<BookingDto> mapBookingDtosFromEntities(List<Booking> bookings) {
    return bookings.stream().map(mapper::mapEntityToDto).collect(Collectors.toList());
  }

  private BookingDto mapBookingDtoFromEntity(Booking booking) {
    return mapper.mapEntityToDto(booking);
  }
}
