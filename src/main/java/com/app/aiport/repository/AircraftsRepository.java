package com.app.aiport.repository;

import com.app.aiport.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AircraftsRepository extends JpaRepository<Aircraft, String> {

    List<Aircraft> findAircraftByModelContaining(String query);
}
