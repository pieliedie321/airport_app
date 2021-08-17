package com.app.aiport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "airports")
public class Airport {

    @Id
    @Column(name = "airport_code", columnDefinition = "bpchar(10)")
    @Schema
    private String airportCode;

    @Column(name = "airport_name")
    @Schema
    private String airportName;

    @Column(name = "city")
    @Schema
    private String city;

    @Column(name = "longitude")
    @Schema
    private Double longitude;

    @Column(name = "latitude")
    @Schema
    private Double latitude;

    @Column(name = "timezone")
    @Schema
    private String timezone;
}
