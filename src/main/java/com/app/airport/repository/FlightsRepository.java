package com.app.airport.repository;

import java.util.Date;
import java.util.List;
import com.app.airport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightsRepository extends JpaRepository<Flight, Integer> {

  @Query(
      value = "SELECT * FROM flights ORDER BY scheduled_departure DESC LIMIT :limit",
      nativeQuery = true)
  List<Flight> findAllFlightsOrderByDepartureDate(@Param("limit") int limit);

  @Query(
      value = "SELECT * FROM flights ORDER BY scheduled_arrival DESC LIMIT :limit",
      nativeQuery = true)
  List<Flight> findAllFlightsOrderByArrivalDate(@Param("limit") int limit);

  @Query(value = "SELECT * FROM flights LIMIT :limit", nativeQuery = true)
  List<Flight> findAllFlightsLimit(@Param("limit") int limit);

  List<Flight> findFlightByFlightNoContaining(String query);

  List<Flight> findFlightsByStatus(String status);

  List<Flight> findFlightsByScheduledDepartureBefore(Date departureDate);

  List<Flight> findFlightsByArrivalAirport(String airport);

  List<Flight> findFlightsByDepartureAirport(String airport);

  List<Flight> findFlightsByArrivalAirportAndDepartureAirport(
      String arrivalAirport, String departureAirport);
}
