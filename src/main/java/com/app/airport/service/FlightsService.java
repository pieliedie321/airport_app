package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.app.airport.config.ResponseConfig;
import com.app.airport.dto.AircraftDto;
import com.app.airport.dto.AirportDto;
import com.app.airport.dto.FlightDto;
import com.app.airport.dto.TicketFlightDto;
import com.app.airport.entity.Flight;
import com.app.airport.repository.FlightsRepository;
import com.app.airport.utils.mapper.FlightsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/** Service for flights repo and mapping. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class FlightsService {

  private final FlightsRepository repository;
  private final FlightsMapper mapper;
  private final AircraftsService aircraftsService;
  private final AirportsService airportsService;
  private final TicketFlightsService ticketFlightsService;
  private final ResponseConfig config;

  @Autowired
  public FlightsService(
      FlightsRepository repository,
      FlightsMapper mapper,
      AircraftsService aircraftsService,
      AirportsService airportsService,
      TicketFlightsService ticketFlightsService,
      ResponseConfig config) {
    this.repository = repository;
    this.mapper = mapper;
    this.aircraftsService = aircraftsService;
    this.airportsService = airportsService;
    this.ticketFlightsService = ticketFlightsService;
    this.config = config;
  }

  public List<FlightDto> findFlights(String flightNo) {
    return isNull(flightNo)
        ? mapFlightsToDto(repository.findAllFlightsLimit(config.getLimit()))
        : mapFlightsToDto(repository.findFlightByFlightNoContaining(flightNo));
  }

  public List<FlightDto> findLatestArrivalFlights() {
    return mapFlightsToDto(repository.findAllFlightsOrderByArrivalDate(config.getLimit()));
  }

  public List<FlightDto> findLatestDepartureFlights() {
    return mapFlightsToDto(repository.findAllFlightsOrderByDepartureDate(config.getLimit()));
  }

  public List<FlightDto> findFlightsByStatus(String status) {
    return mapFlightsToDto(repository.findFlightsByStatus(status));
  }

  public List<FlightDto> findFlightsByDepartureDate(Date departureDate) {
    return mapFlightsToDto(repository.findFlightsByScheduledDepartureBefore(departureDate));
  }

  public List<FlightDto> getFlightsByAirport(String arrival, String departure) {
    if (isNull(arrival) && isNull(departure)) {
      throw new IllegalArgumentException("Arrival and departure can't be combined null");
    } else {
      if (isNull(departure)) {
        return mapFlightsToDto(repository.findFlightsByArrivalAirport(arrival));
      } else if (isNull(arrival)) {
        return mapFlightsToDto(repository.findFlightsByDepartureAirport(departure));
      } else {
        return mapFlightsToDto(
            repository.findFlightsByArrivalAirportAndDepartureAirport(arrival, departure));
      }
    }
  }

  public FlightDto getFlightById(@NotNull Integer id) {
    return mapFlightToDto(
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cannot find entity with id: " + id)));
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void saveNewFlight(FlightDto flightDto) {
    saveEntities(flightDto);
  }

  private void saveEntities(FlightDto flightDto) {
    repository.save(mapFlightEntityFromDto(flightDto));
    ticketFlightsService.saveNewTicketFLights(flightDto.getTicketFlights());
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void deleteFlight(Integer id, FlightDto flightDto) {
    if (isNull(flightDto)) {
      deleteFlightById(id);
    } else {
      deleteEntities(flightDto);
    }
  }

  private void deleteFlightById(Integer id) {
    repository.deleteById(id);
  }

  private void deleteEntities(FlightDto flightDto) {
    repository.delete(mapFlightEntityFromDto(flightDto));
    if (!isNull(flightDto.getTicketFlights()) || !flightDto.getTicketFlights().isEmpty()) {
      ticketFlightsService.deleteTicketFlights(flightDto.getTicketFlights());
    }
  }

  private FlightDto mapFlightToDto(Flight flight) {
    return mapper.mapEntityToDto(
        flight,
        getTicketFlightDtos(flight.getFlightId()),
        getAircraftDto(flight.getAircraftCode()),
        getAirportDto(flight.getDepartureAirport()),
        getAirportDto(flight.getArrivalAirport()));
  }

  private List<FlightDto> mapFlightsToDto(List<Flight> flights) {
    return flights.stream()
        .map(
            flight ->
                mapper.mapEntityToDto(
                    flight,
                    getTicketFlightDtos(flight.getFlightId()),
                    getAircraftDto(flight.getAircraftCode()),
                    getAirportDto(flight.getDepartureAirport()),
                    getAirportDto(flight.getArrivalAirport())))
        .collect(Collectors.toList());
  }

  private Flight mapFlightEntityFromDto(FlightDto flightDto) {
    return mapper.mapDtoToEntity(flightDto);
  }

  private List<TicketFlightDto> getTicketFlightDtos(Integer flightId) {
    return ticketFlightsService.findAllByFlightId(flightId);
  }

  private AircraftDto getAircraftDto(String aircraftCode) {
    return aircraftsService.findAircraftById(aircraftCode);
  }

  private AirportDto getAirportDto(String airportCode) {
    return airportsService.findAirportById(airportCode);
  }
}
