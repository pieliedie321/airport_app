package com.app.airport.controller;

import com.app.airport.service.BoardingPassesService;
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
@WebMvcTest(BoardingPassesController.class)
public class BoardingPassesControllerTest {

  @Autowired private BoardingPassesController boardingPassesController;

  @Autowired private MockMvc mockMvc;

  @MockBean private BoardingPassesService boardingPassesService;

  @Test
  public void controllerInitializedCorrectly() {
    assertThat(boardingPassesController).isNotNull();
  }

  @Test
  public void testGetAirports() throws Exception {
    mockMvc
        .perform(get("/bpasses"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
