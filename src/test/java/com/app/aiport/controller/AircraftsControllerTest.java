package com.app.aiport.controller;

import com.app.aiport.controller.AircraftsController;
import com.app.aiport.service.AircraftsService;
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
@WebMvcTest(AircraftsController.class)
public class AircraftsControllerTest {

  @Autowired private AircraftsController aircraftsController;

  @Autowired private MockMvc mockMvc;

  @MockBean private AircraftsService aircraftsService;

  @Test
  public void controllerInitializedCorrectly() {
    assertThat(aircraftsController).isNotNull();
  }

  @Test
  public void testGetAircrafts() throws Exception {
    mockMvc
        .perform(get("/aircrafts"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
