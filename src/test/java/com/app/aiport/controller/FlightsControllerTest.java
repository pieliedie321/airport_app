package com.app.aiport.controller;

import com.app.aiport.service.FlightsService;
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
@WebMvcTest(FlightsController.class)
public class FlightsControllerTest {

  @Autowired
  private FlightsController flightsController;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FlightsService flightsService;

  @Test
  public void controllerInitializedCorrectly() {
    assertThat(flightsController).isNotNull();
  }

  @Test
  public void testGetFlights() throws Exception {
    mockMvc.perform(get("/flights"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  //TODO переделать данный тест. Пока кидает exception ContentTypeNotSet
//  @Test
//  public void testGetFlightById() throws Exception {
//    mockMvc.perform(get("/flights/id/1"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//  }
}
