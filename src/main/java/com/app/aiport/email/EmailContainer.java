package com.app.aiport.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Контейнер полей для отправки почты */
@Data
@AllArgsConstructor
public class EmailContainer {

  @NotBlank private String fullName;
  private String bodyText;
  @Email private String mailTo;
  @NotBlank private String subject;
}
