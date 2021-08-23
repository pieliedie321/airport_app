package com.app.aiport.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import com.app.aiport.utils.EmailServiceUtil;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.thymeleaf.spring5.SpringTemplateEngine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MailClientTest {

    private static final String FULL_NAME = "test name";
    private static final String EMAIL_TO = "someone@localhost.com";
    private static final String EMAIL_SUBJECT = "Test E-Mail";
    private static final String EMAIL_TEXT = "This is a test e-mail.";

    @Autowired
    private EmailServiceUtil emailService;

    private GreenMail smtpServer;

    @Before
    public void setUp() {
        smtpServer = new GreenMail(new ServerSetup(3025, null, "smtp"));
        smtpServer.start();
    }

    @After
    public void tearDown() {
        smtpServer.stop();
    }

    @Test
    public void shouldSendMail() throws Exception {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(3025);
        mailSender.setUsername("admin");
        mailSender.setPassword("admin");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        emailService = new EmailServiceUtil(mailSender, new SpringTemplateEngine());

        EmailContainer email = new EmailContainer(FULL_NAME, EMAIL_TEXT, EMAIL_TO, EMAIL_SUBJECT);
        emailService.sendEmail(email);

        String content = "<span>" + EMAIL_TEXT + "</span>";
        assertReceivedMessageContains(content);
    }

    private void assertReceivedMessageContains(String expected) throws IOException, MessagingException {
        assertTrue(smtpServer.waitForIncomingEmail(5000, 1));
        MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        assertEquals(EMAIL_TEXT, receivedMessages[0].getSubject());
        String content = (String) receivedMessages[0].getContent();
        assertTrue(content.contains(expected));
    }
}
