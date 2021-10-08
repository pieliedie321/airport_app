package com.app.airport.repository;

import java.math.BigDecimal;
import java.util.List;
import com.app.airport.entity.TicketFlight;
import com.app.airport.entity.composite.CompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketFlightsRepository extends JpaRepository<TicketFlight, CompositeId> {

  List<TicketFlight> findTicketFlightsByFlightId(Integer id);
}
