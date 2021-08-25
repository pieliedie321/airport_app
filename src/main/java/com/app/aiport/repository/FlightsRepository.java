package com.app.aiport.repository;

import java.util.Date;
import java.util.List;

import com.app.aiport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightsRepository extends JpaRepository<Flight, Integer> {

  List<Flight> findFlightByFlightNoContaining(String query);

  List<Flight> findFlightsByStatus(String status);

  List<Flight> findFlightsByScheduledDepartureBefore(Date departureDate);

  List<Flight> findFlightsByArrivalAirport(String airport);

  List<Flight> findFlightsByDepartureAirport(String airport);

  List<Flight> findFlightsByArrivalAirportAndDepartureAirport(String arrivalAirport, String departureAirport);
}
