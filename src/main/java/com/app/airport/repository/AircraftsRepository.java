package com.app.airport.repository;

import com.app.airport.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftsRepository extends JpaRepository<Aircraft, String> {}
