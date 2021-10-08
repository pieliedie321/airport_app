package com.app.airport.utils.mapper;

import com.app.airport.dto.BoardPassDto;
import com.app.airport.entity.BoardPass;
import org.springframework.stereotype.Component;

@Component
public class BoardPassesMapper {

  public BoardPassDto mapEntityToDto(BoardPass boardPass) {
    return BoardPassDto.builder()
        .ticketNo(boardPass.getTicketNo())
        .flightId(boardPass.getFlightId())
        .boardingNo(boardPass.getBoardingNo())
        .seatNo(boardPass.getSeatNo())
        .build();
  }

  public BoardPass mapDtoToEntity(BoardPassDto boardPassDto) {
    BoardPass boardPass = new BoardPass();
    boardPass.setTicketNo(boardPassDto.getTicketNo());
    boardPass.setFlightId(boardPassDto.getFlightId());
    boardPass.setBoardingNo(boardPassDto.getBoardingNo());
    boardPass.setSeatNo(boardPassDto.getSeatNo());
    return boardPass;
  }
}
