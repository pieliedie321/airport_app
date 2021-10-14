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

  @Query(
      value = "SELECT * FROM flights WHERE flight_no LIKE %:query% LIMIT :limit",
      nativeQuery = true)
  List<Flight> findFlightByFlightNoContaining(
      @Param("query") String query, @Param("limit") int limit);

  @Query(
      value = "SELECT * FROM flights WHERE status LIKE %:status% LIMIT :limit",
      nativeQuery = true)
  List<Flight> findFlightsByStatus(@Param("status") String status, @Param("limit") int limit);

  @Query(
      value = "SELECT * FROM flights WHERE :departureDate < scheduled_arrival LIMIT :limit",
      nativeQuery = true)
  List<Flight> findFlightsByScheduledDepartureBefore(
      @Param("departureDate") Date departureDate, @Param("limit") int limit);

  @Query(
      value = "SELECT * FROM flights WHERE arrival_airport LIKE %:arrivalAirport% LIMIT :limit",
      nativeQuery = true)
  List<Flight> findFlightsByArrivalAirport(
      @Param("arrivalAirport") String airport, @Param("limit") int limit);

  @Query(
      value = "SELECT * FROM flights WHERE departure_airport LIKE %:departureAirport% LIMIT :limit",
      nativeQuery = true)
  List<Flight> findFlightsByDepartureAirport(
      @Param("departureAirport") String airport, @Param("limit") int limit);

  @Query(
      value =
          "SELECT * FROM flights WHERE arrival_airport LIKE %:arrivalAirport% AND departure_airport LIKE %:departureAirport% LIMIT :limit",
      nativeQuery = true)
  List<Flight> findFlightsByArrivalAirportAndDepartureAirport(
      @Param("arrivalAirport") String arrivalAirport,
      @Param("departureAirport") String departureAirport,
      @Param("limit") int limit);
}
