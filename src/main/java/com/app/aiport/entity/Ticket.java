package com.app.aiport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_no", columnDefinition = "bpchar(13)")
    private String ticketNo;

    @Column(name = "book_ref", columnDefinition = "bpchar(6)")
    private String bookRef;

    @Column(name = "passenger_id")
    private String passengerId;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "contact_data", columnDefinition = "jsonb")
    private String contactData;
}
