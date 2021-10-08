package com.app.airport.repository;

import java.util.List;
import com.app.airport.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, String> {

    @Query(value = "SELECT * FROM bookings WHERE book_ref = :id", nativeQuery = true)
    List<Booking> findBookingsById(@Param("id") String id);
}
