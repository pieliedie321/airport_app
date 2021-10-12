package com.app.airport.service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import com.app.airport.dto.BoardPassDto;
import com.app.airport.dto.TicketDto;
import com.app.airport.dto.TicketFlightDto;
import com.app.airport.entity.TicketFlight;
import com.app.airport.entity.composite.CompositeId;
import com.app.airport.repository.TicketFlightsRepository;
import com.app.airport.utils.mapper.TicketFlightsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for ticketFlights repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class TicketFlightsService {

  private final TicketFlightsRepository repository;
  private final TicketFlightsMapper mapper;
  private final BoardingPassesService boardingPassesService;
  private final TicketsService ticketsService;

  @Autowired
  public TicketFlightsService(
      TicketFlightsRepository repository,
      TicketFlightsMapper mapper,
      BoardingPassesService boardingPassesService,
      TicketsService ticketsService) {
    this.repository = repository;
    this.mapper = mapper;
    this.boardingPassesService = boardingPassesService;
    this.ticketsService = ticketsService;
  }

  public List<TicketFlightDto> findAllByFlightId(Integer id) {
    return mapTicketFlightDtosFromEntities(repository.findTicketFlightsByFlightId(id));
  }

  public void saveNewTicketFLights(List<TicketFlightDto> ticketFlightDtos) {
    for (TicketFlightDto ticketFlightDto : ticketFlightDtos) {
      saveNewTicketFlight(ticketFlightDto);
    }
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void saveNewTicketFlight(TicketFlightDto ticketFlightDto) {
    if (!isNull(ticketFlightDto) && !isNull(ticketFlightDto.getTicket().getTicketNo())) {
      repository.save(mapTicketFlightEntityFromDto(ticketFlightDto));
      ticketsService.saveNewTicket(ticketFlightDto.getTicket());
      boardingPassesService.saveNewBoardingPass(ticketFlightDto.getBoardPass());
    }
  }

  public void deleteTicketFlights(List<TicketFlightDto> ticketFlightDtos) {
    for (TicketFlightDto ticketFlightDto : ticketFlightDtos) {
      deleteTicketFLightById(
          new CompositeId(ticketFlightDto.getTicket().getTicketNo(), ticketFlightDto.getFlightId()),
          ticketFlightDto.getTicket().getTicketNo());
    }
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteTicketFLightById(CompositeId id, String ticketNo) {
    repository.deleteById(id);
    ticketsService.deleteTicket(ticketNo);
    boardingPassesService.deleteBoardingPass(id);
  }

  private List<TicketFlightDto> mapTicketFlightDtosFromEntities(List<TicketFlight> ticketFlights) {
    return ticketFlights.stream()
        .map(
            ticketFlight ->
                mapper.mapEntityToDto(
                    ticketFlight,
                    getTicketDto(ticketFlight.getTicketNo()),
                    getBoardPassDto(
                        new CompositeId(ticketFlight.getTicketNo(), ticketFlight.getFlightId()))))
        .collect(Collectors.toList());
  }

  private TicketFlight mapTicketFlightEntityFromDto(TicketFlightDto ticketFlightDto) {
    return mapper.mapDtoToEntity(ticketFlightDto);
  }

  private TicketDto getTicketDto(String ticketNo) {
    return ticketsService.findTicket(ticketNo);
  }

  private BoardPassDto getBoardPassDto(CompositeId id) {
    return boardingPassesService.findBoardingPassById(id);
  }
}
