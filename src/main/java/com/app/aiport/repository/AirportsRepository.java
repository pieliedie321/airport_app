package com.app.aiport.repository;

import java.util.List;

import com.app.aiport.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportsRepository extends JpaRepository<Airport, String> {

    List<Airport> findAirportsByAirportNameContaining(String query);

    List<Airport> findAirportsByCityContaining(String query);

    List<Airport> findAirportsByTimezoneContaining(String query);
}
