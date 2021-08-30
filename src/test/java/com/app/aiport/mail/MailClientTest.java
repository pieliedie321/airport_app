//package com.app.aiport;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//
//import com.app.aiport.email.EmailContainer;
//import com.app.aiport.utils.EmailServiceUtil;
//import com.icegreen.greenmail.util.GreenMail;
//import com.icegreen.greenmail.util.ServerSetup;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//public class MailClientTest {
//
//  private static final String FULL_NAME = "test name";
//  private static final String EMAIL_TO = "someone@localhost.com";
//  private static final String EMAIL_SUBJECT = "Test E-Mail";
//  private static final String EMAIL_TEXT = "This is a test e-mail.";
//
//  @MockBean
//  EmailServiceUtil emailService;
//
//  @Autowired
//  ApplicationContext context;
//
//  @Autowired
//  private JavaMailSender mailSender;
//
//  @Autowired
//  private SpringTemplateEngine springTemplateEngine;
//
//  private GreenMail smtpServer;
//
//  @Before
//  public void setUp() {
//    smtpServer = new GreenMail(new ServerSetup(3025, null, "smtp"));
//    smtpServer.start();
//  }
//
//  @After
//  public void tearDown() {
//    smtpServer.stop();
//  }
//
//  @Test
//  public void shouldSendMail() throws Exception {
//    mailSender = context.getBean(JavaMailSenderImpl.class);
//    springTemplateEngine = context.getBean(SpringTemplateEngine.class);
//
//    emailService = new EmailServiceUtil(mailSender, springTemplateEngine);
//
//    EmailContainer email = new EmailContainer(FULL_NAME, EMAIL_TEXT, EMAIL_TO, EMAIL_SUBJECT);
//    emailService.sendEmail(email);
//
//    String content = "<span>" + EMAIL_TEXT + "</span>";
//    assertReceivedMessageContains(content);
//  }
//
//  private void assertReceivedMessageContains(String expected) throws IOException, MessagingException {
//    assertTrue(smtpServer.waitForIncomingEmail(5000, 1));
//    MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
//    assertEquals(1, receivedMessages.length);
//    assertEquals(EMAIL_TEXT, receivedMessages[0].getSubject());
//    String content = (String) receivedMessages[0].getContent();
//    assertTrue(content.contains(expected));
//  }
//}
