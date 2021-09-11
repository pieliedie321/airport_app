package com.app.airport.repository;

import java.util.List;

import com.app.airport.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends JpaRepository<Seat, String> {

  List<Seat> findSeatsByAircraftCode(String code);

  List<Seat> findSeatsByFareConditions(String condition);
}
