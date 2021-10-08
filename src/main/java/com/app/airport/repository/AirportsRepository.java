package com.app.airport.repository;

import java.util.List;
import com.app.airport.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportsRepository extends JpaRepository<Airport, String> {}
