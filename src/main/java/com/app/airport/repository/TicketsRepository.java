package com.app.airport.repository;

import java.util.List;
import com.app.airport.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, String> {

  List<Ticket> findTicketsByPassengerId(String passengerId);
}
