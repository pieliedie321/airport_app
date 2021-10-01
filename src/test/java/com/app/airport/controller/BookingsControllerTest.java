package com.app.airport.controller;

import com.app.airport.service.BookingsService;
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
@WebMvcTest(BookingsController.class)
public class BookingsControllerTest {

  @Autowired private BookingsController bookingsController;

  @Autowired private MockMvc mockMvc;

  @MockBean private BookingsService bookingsService;

  @Test
  public void controllerInitializedCorrectly() {
    assertThat(bookingsController).isNotNull();
  }

  @Test
  public void testGetAirports() throws Exception {
    mockMvc
        .perform(get("/bookings"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
