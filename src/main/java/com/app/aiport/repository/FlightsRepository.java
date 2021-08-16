package com.app.aiport.repository;

import com.app.aiport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightsRepository extends JpaRepository<Flight, Integer> {

    List<Flight> findFlightByFlightNoContaining(String query);
}
