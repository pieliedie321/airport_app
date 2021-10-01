package com.app.airport.repository;

import com.app.airport.entity.BoardPass;
import com.app.airport.entity.composite.CompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardingPassesRepository extends JpaRepository<BoardPass, CompositeId> {
}
