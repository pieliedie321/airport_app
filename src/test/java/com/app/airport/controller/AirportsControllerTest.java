package com.app.airport.controller;

import com.app.airport.service.AirportsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AirportsController.class)
public class AirportsControllerTest {

  @Autowired private AirportsController airportsController;

  @Autowired private MockMvc mockMvc;

  @MockBean private AirportsService airportsService;

  @Test
  public void controllerInitializedCorrectly() {
    assertThat(airportsController).isNotNull();
  }

  @Test
  public void testGetAirports() throws Exception {
    mockMvc
        .perform(get("/airports"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testGetAirportsByCity() throws Exception {
    mockMvc
        .perform(get("/airports/city/Воронеж"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testGetFlightsByAirport() throws Exception {
    mockMvc
        .perform(get("/airports/timezone/Europe"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
