package com.app.aiport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    @Schema
    private Integer flightId;

    @Column(name = "flight_no", columnDefinition = "bpchar(10)")
    @NotBlank(message = "Field flightNo can't be null")
    @Schema
    private String flightNo;

    @Column(name = "scheduled_departure")
    @Schema
    private Date scheduledDeparture;

    @Column(name = "scheduled_arrival")
    @Schema
    private Date scheduledArrival;

    @Column(name = "departure_airport", columnDefinition = "bpchar(3)")
    @Schema
    private String departureAirport;

    @Column(name = "arrival_airport", columnDefinition = "bpchar(3)")
    @Schema
    private String arrivalAirport;

    @Column(name = "status")
    @Schema
    private String status;

    @Column(name = "aircraft_code", columnDefinition = "bpchar(3)")
    @Schema
    private String aircraftCode;

    @Column(name = "actual_departure")
    @Schema
    private Date actualDeparture;

    @Column(name = "actual_arrival")
    @Schema
    private Date actualArrival;
}
