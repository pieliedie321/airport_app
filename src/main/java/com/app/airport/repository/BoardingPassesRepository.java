package com.app.airport.repository;

import com.app.airport.entity.BoardPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardingPassesRepository extends JpaRepository<BoardPass, String> {
}
