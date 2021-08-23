package com.app.aiport.repository;

import com.app.aiport.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Booking, String> {
}
