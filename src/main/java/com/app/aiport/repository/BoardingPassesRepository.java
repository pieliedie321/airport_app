package com.app.aiport.repository;

import com.app.aiport.entity.BoardPass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardingPassesRepository extends JpaRepository<BoardPass, String> {
}
