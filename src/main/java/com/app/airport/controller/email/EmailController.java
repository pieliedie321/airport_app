package com.app.airport.controller.email;

import javax.mail.MessagingException;
import javax.validation.Valid;
import com.app.airport.email.EmailContainer;
import com.app.airport.service.EmailServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/emails")
public class EmailController {

  private final EmailServiceUtil service;

  @Autowired
  public EmailController(EmailServiceUtil service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<String> sendMail(@RequestBody @Valid EmailContainer emailContainer)
      throws MessagingException {
    service.sendEmail(emailContainer);
    return ResponseEntity.ok("Email sent!");
  }
}
