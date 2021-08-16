package com.app.aiport.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Integer flightId;

    @Column(name = "flight_no", columnDefinition = "bpchar(10)")
    private String flightNo;

    @Column(name = "scheduled_departure")
    private Date scheduledDeparture;

    @Column(name = "scheduled_arrival")
    private Date scheduledArrival;

    @Column(name = "departure_airport", columnDefinition = "bpchar(10)")
    private String departureAirport;

    @Column(name = "arrival_airport", columnDefinition = "bpchar(10)")
    private String arrivalAirport;

    @Column(name = "status")
    private String status;

    @Column(name = "aircraft_code", columnDefinition = "bpchar(10)")
    private Character aircraftCode;

    @Column(name = "actual_departure")
    private Date actualDeparture;

    @Column(name = "actual_arrival")
    private Date actualArrival;


}
