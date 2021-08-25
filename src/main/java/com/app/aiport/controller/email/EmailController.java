package com.app.aiport.controller.email;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.app.aiport.email.EmailContainer;
import com.app.aiport.utils.EmailServiceUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/emails")
@AllArgsConstructor
public class EmailController {

  private final EmailServiceUtil emailService;

  @PostMapping
  @ResponseBody
  public ResponseEntity<String> sendMail(@RequestBody @Valid EmailContainer emailContainer) throws MessagingException {
    emailService.sendEmail(emailContainer);
    return ResponseEntity.ok("Email sent!");
  }
}
