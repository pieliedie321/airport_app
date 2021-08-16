package com.app.aiport.repository;

import com.app.aiport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightsRepository extends JpaRepository<Flight, Long> {
}
