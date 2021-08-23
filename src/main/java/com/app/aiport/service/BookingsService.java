package com.app.aiport.service;

import javax.transaction.Transactional;
import java.util.List;

import com.app.aiport.entity.Booking;
import com.app.aiport.repository.BookingsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(value = Transactional.TxType.SUPPORTS)
public class BookingsService {

    private final BookingsRepository bookingsRepository;

    private final String DELETED = "Booking deleted, with code: ";

    public List<Booking> getAllBookings() {
        return bookingsRepository.findAll();
    }

    public Booking getBookingById(String id) {
        return bookingsRepository.getById(id);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Booking saveNewBooking(Booking booking) {
        log.debug("Saving new booking with code: " + booking.getBookingRef());
        return bookingsRepository.save(booking);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public String deleteBookingsByCode(String code) {
        log.debug("Deleting booking with id: " + code);
        bookingsRepository.deleteById(code);
        return DELETED;
    }
}

