package com.app.airport.utils.mapper;

import java.util.HashMap;
import java.util.List;
import com.app.airport.dto.BookingDto;
import com.app.airport.dto.TicketDto;
import com.app.airport.entity.Ticket;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import static java.util.Objects.isNull;

@Component
public class TicketMapper {

  public TicketDto mapEntityToDto(Ticket ticket, List<BookingDto> bookingDtos) {
    HashMap<String, String> contactData = parseContactData(ticket.getContactData());
    return TicketDto.builder()
        .ticketNo(ticket.getTicketNo())
        .bookRef(ticket.getBookRef())
        .passengerId(ticket.getPassengerId())
        .passengerName(ticket.getPassengerName())
        .phoneNumber(contactData.get("phone"))
        .email(contactData.get("email"))
        .booking(bookingDtos)
        .build();
  }

  public Ticket mapDtoToEntity(TicketDto ticketDto) {
    Ticket ticket = new Ticket();
    ticket.setTicketNo(ticketDto.getTicketNo());
    ticket.setBookRef(ticketDto.getBookRef());
    ticket.setPassengerId(ticketDto.getPassengerId());
    ticket.setPassengerName(ticketDto.getPassengerName());
    ticket.setContactData(makeContactData(ticketDto.getEmail(), ticketDto.getPhoneNumber()));
    return ticket;
  }

  private HashMap<String, String> parseContactData(String contactData) {
    HashMap<String, String> data = new HashMap<>();
    JSONObject dataJsonObj = new JSONObject(contactData);
    if (contactData.contains("email")) {
      data.put("email", dataJsonObj.getString("email"));
    }
    if (contactData.contains("phone")) {
      data.put("phone", dataJsonObj.getString("phone"));
    }
    return data;
  }

  private String makeContactData(String email, String phone) {
    JSONObject dataJsonObj = new JSONObject();
    if (!isNull(email)) {
      dataJsonObj.append("email", email);
    }
    if (!isNull(phone)) {
      dataJsonObj.append("phone", phone);
    }
    return dataJsonObj.toString();
  }
}
