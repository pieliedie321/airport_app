package com.app.airport.repository;

import java.util.List;

import com.app.airport.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftsRepository extends JpaRepository<Aircraft, String> {

  List<Aircraft> findAircraftByModelContaining(String query);

  List<Aircraft> findAircraftsByRangeGreaterThan(Integer range);

  List<Aircraft> findAircraftsByRangeLessThan(Integer range);
}
