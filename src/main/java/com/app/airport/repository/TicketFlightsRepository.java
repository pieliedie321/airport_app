package com.app.airport.repository;

import java.math.BigDecimal;
import java.util.List;

import com.app.airport.entity.TicketFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketFlightsRepository extends JpaRepository<TicketFlight, String> {

  List<TicketFlight> findTicketFlightsByFlightId(Integer id);

  List<TicketFlight> findTicketFlightsByFareConditions(String condition);

  List<TicketFlight> findTicketFlightsByAmount(BigDecimal amount);

  List<TicketFlight> findTicketFlightsByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
}
