package com.app.airport.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/** Email sending container */
@Data
@AllArgsConstructor
public class EmailContainer {

  private String bodyText;

  @NotBlank
  private String fullName;

  @Email
  private String mailTo;

  @NotBlank
  private String subject;
}
