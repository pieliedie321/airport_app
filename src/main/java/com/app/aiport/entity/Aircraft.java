package com.app.aiport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "aircrafts")
public class Aircraft {

    @Id
    @Column(name = "aircraft_code", columnDefinition = "bpchar(3)")
    @Schema
    private String code;

    @Column(name = "model")
    private String model;

    @Column(name = "range")
    private Integer range;
}
