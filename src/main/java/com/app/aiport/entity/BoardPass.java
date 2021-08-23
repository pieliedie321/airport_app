package com.app.aiport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "boarding_passes")
public class BoardPass {

    @Id
    @Column(name = "ticket_no", columnDefinition = "bpchar(13)")
    private String ticketNo;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    Flight flight;

    @Column(name = "boarding_no")
    private Integer boardingNo;

    @Column(name = "seat_no")
    private String seatNo;
}
