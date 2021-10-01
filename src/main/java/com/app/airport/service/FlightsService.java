package com.app.airport.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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

/** Service for flights repo. */
@Slf4j
@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class FlightsService {

  private final FlightsRepository repository;
  private final FlightsMapper mapper;
  private final AircraftsService aircraftsService;
  private final AirportsService airportsService;
  private final TicketFlightsService ticketFlightsService;
  private final String DELETED = "Flight was deleted, with id: ";

  @Autowired
  public FlightsService(
      FlightsRepository repository,
      FlightsMapper mapper,
      AircraftsService aircraftsService,
      AirportsService airportsService,
      TicketFlightsService ticketFlightsService) {
    this.repository = repository;
    this.mapper = mapper;
    this.aircraftsService = aircraftsService;
    this.airportsService = airportsService;
    this.ticketFlightsService = ticketFlightsService;
  }

  public List<Flight> findFlights(String flightNo) {
    return isNull(flightNo)
        ? repository.findAll()
        : repository.findFlightByFlightNoContaining(flightNo);
  }

  public List<Flight> findFlightsByStatus(String status) {
    return repository.findFlightsByStatus(status);
  }

  public List<Flight> findFlightsByDepartureDate(Date departureDate) {
    return repository.findFlightsByScheduledDepartureBefore(departureDate);
  }

  public List<Flight> getFlightsByAirport(String arrival, String departure) {
    if (isNull(arrival) && isNull(departure)) {
      throw new IllegalArgumentException("Arrival and departure can't be combined null");
    } else {
      if (isNull(departure)) {
        return repository.findFlightsByArrivalAirport(arrival);
      } else if (isNull(arrival)) {
        return repository.findFlightsByDepartureAirport(departure);
      } else {
        return repository.findFlightsByArrivalAirportAndDepartureAirport(arrival, departure);
      }
    }
  }

  public Flight getFlightById(Integer id) {
    return repository.findById(id).orElse(null);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Flight saveNewFlight(Flight flight) {
    log.debug(
        "Saving new flight with id: "
            + flight.getFlightId()
            + ", and flightNo: "
            + flight.getFlightNo());
    return repository.save(flight);
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public String deleteFlightById(Integer id) {
    log.debug("Deleting flight with id: " + id);
    repository.deleteById(id);
    return DELETED;
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public Flight updateFlight(Flight newFLight, Integer id) {
    return repository
        .findById(id)
        .map(
            flight -> {
              flight.setFlightId(id);
              flight.setFlightNo(newFLight.getFlightNo());
              flight.setScheduledDeparture(newFLight.getScheduledDeparture());
              flight.setScheduledArrival(newFLight.getScheduledArrival());
              flight.setDepartureAirport(newFLight.getDepartureAirport());
              flight.setArrivalAirport(newFLight.getArrivalAirport());
              flight.setStatus(newFLight.getStatus());
              flight.setAircraftCode(newFLight.getAircraftCode());
              flight.setActualArrival(newFLight.getActualArrival());
              flight.setActualDeparture(newFLight.getActualDeparture());
              return repository.save(flight);
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    "Cannot find entity \"Flight\" to update, with id: " + id));
  }

  public List<FlightDto> constructFlightDtoFromEntities() {
    return mapFlightsToDto(repository.findFlightsForDto());
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

  private List<TicketFlightDto> getTicketFlightDtos(Integer flightId) {
    return ticketFlightsService.constructTicketFlightDtosFromEntities(flightId);
  }

  private AircraftDto getAircraftDto(String aircraftCode) {
    return aircraftsService.constructAircraftDtoFromEntity(aircraftCode);
  }

  private AirportDto getAirportDto(String airportCode) {
    return airportsService.constructAircportDtoFromEntity(airportCode);
  }
}
