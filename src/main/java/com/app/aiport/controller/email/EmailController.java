package com.app.aiport.controller.email;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.app.aiport.email.EmailContainer;
import com.app.aiport.utils.EmailServiceUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/emails")
@AllArgsConstructor
public class EmailController {

  private final EmailServiceUtil emailService;

  @PostMapping
  public ResponseEntity<String> sendMail(@RequestBody @Valid EmailContainer emailContainer)
      throws MessagingException {
    emailService.sendEmail(emailContainer);
    return ResponseEntity.ok("Email sent!");
  }
}
