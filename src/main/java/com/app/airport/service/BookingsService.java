package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
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

  @Autowired
  public BookingsService(BookingsRepository repository, BookingsMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<BookingDto> findBookingsById(String id) {
    return Optional.of(mapBookingDtosFromEntities(repository.findBookingsById(id)))
        .orElseThrow(() -> new EntityNotFoundException("Can't find bookings with id: " + id));
  }

  public BookingDto findBookingById(String id) {
    return mapBookingDtoFromEntity(
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Can't find booking with id " + id)));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void saveBookings(@NotEmpty @NotNull List<BookingDto> bookingDtos) {
    for (BookingDto bookingDto : bookingDtos) {
      saveNewBooking(bookingDto);
    }
  }

  private void saveNewBooking(@NotNull BookingDto bookingDto) {
    repository.save(mapBookingEntityFromDto(bookingDto));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteBookings(@NotEmpty @NotNull List<BookingDto> bookingDtos) {
    for (BookingDto bookingDto : bookingDtos) {
      deleteBooking(bookingDto.getBookingRef());
    }
  }

  private void deleteBooking(String code) {
    repository.deleteById(code);
  }

  private List<BookingDto> mapBookingDtosFromEntities(List<Booking> bookings) {
    return bookings.stream().map(mapper::mapEntityToDto).collect(Collectors.toList());
  }

  private BookingDto mapBookingDtoFromEntity(Booking booking) {
    return mapper.mapEntityToDto(booking);
  }


  private Booking mapBookingEntityFromDto(BookingDto bookingDto) {
    return mapper.mapDtoToEntity(bookingDto);
  }
}
