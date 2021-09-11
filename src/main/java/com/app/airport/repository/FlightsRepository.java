package com.app.airport.repository;

import java.util.Date;
import java.util.List;

import com.app.airport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightsRepository extends JpaRepository<Flight, Integer> {

  List<Flight> findFlightByFlightNoContaining(String query);

  List<Flight> findFlightsByStatus(String status);

  List<Flight> findFlightsByScheduledDepartureBefore(Date departureDate);

  List<Flight> findFlightsByArrivalAirport(String airport);

  List<Flight> findFlightsByDepartureAirport(String airport);

  List<Flight> findFlightsByArrivalAirportAndDepartureAirport(String arrivalAirport, String departureAirport);
}
