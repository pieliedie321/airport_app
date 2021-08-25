package com.app.aiport.repository;

import java.util.List;

import com.app.aiport.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftsRepository extends JpaRepository<Aircraft, String> {

  List<Aircraft> findAircraftByModelContaining(String query);

  List<Aircraft> findAircraftsByRangeGreaterThan(Integer range);

  List<Aircraft> findAircraftsByRangeLessThan(Integer range);
}
