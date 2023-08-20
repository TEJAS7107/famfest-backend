package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserMessage;
import com.twilio.rest.preview.wireless.Sim;

import jakarta.activation.DataHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class EmailSendService {

    // @Bean
    // public void SendMail(UserMessage messageBody) {

    // }

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String username, String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(username);
        // message.setText();
        message.setText("Senders Mail ID : " + email + "                                " + "Message : " + text);
        javaMailSender.send(message);
    }

    public void verifyMail(String To, String Otp) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(To);
        simpleMailMessage.setSubject("Verification Otp");
        simpleMailMessage.setText(Otp);

        javaMailSender.send(simpleMailMessage);

    }

    public void sendPdftoEmail(String Email, String EventName, byte[] parts) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        // message.setTo(Email);
        // message.setSubject("Ticket for Event :" + "");
        // MimeMessageHelper helper = new MimeMessageHelper();
        // MimeBodyPart mimeBodyPart = new MimeBodyPart();
        // MimeMultipart mimeMultipart = new MimeMultipart();
        // ByteArrayDataSource ds = new ByteArrayDataSource(parts, "application/pdf");
        // mimeBodyPart.setDataHandler(new DataHandler(ds));
        // mimeBodyPart.setFileName("Ticket.pdf");
        // mimeMultipart.addBodyPart(mimeBodyPart);
        // javaMailSenderImpl.se
        javaMailSenderImpl.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                // set from, to, subject using helper
                helper.setTo("tejaskadam7107@gmail.com");
                helper.setSubject("Ticket for Event:" + EventName);
                helper.addAttachment("Ticket.pdf", new ByteArrayResource(parts));
            }
        });

    }

}
