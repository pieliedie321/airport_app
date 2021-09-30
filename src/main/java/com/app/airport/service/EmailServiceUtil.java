package com.app.airport.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import com.app.airport.email.EmailContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/** EMail sending util. */
@Slf4j
@Service
public class EmailServiceUtil {

  private static final String DEFAULT_TEMPLATE = "/emails/email";

  private final JavaMailSender javaMailSender;
  private final SpringTemplateEngine templateEngine;

  @Autowired
  public EmailServiceUtil(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
    this.javaMailSender = javaMailSender;
    this.templateEngine = templateEngine;
  }

  public void sendEmail(EmailContainer emailContainer) throws MessagingException {

    Context context = new Context();
    context.setVariable("email", emailContainer);

    String process = templateEngine.process(DEFAULT_TEMPLATE, context);

    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setSubject(emailContainer.getSubject());
    helper.setText(emailContainer.getBodyText());
    helper.setTo(emailContainer.getMailTo());

    log.debug(
        "Sending email "
            + emailContainer.getSubject()
            + ", to user: "
            + emailContainer.getFullName());

    javaMailSender.send(message);
  }
}
