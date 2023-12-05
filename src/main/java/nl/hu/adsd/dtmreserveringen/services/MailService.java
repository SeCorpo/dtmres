package nl.hu.adsd.dtmreserveringen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service("emailService")
public class MailService {
    
    @Autowired
    private JavaMailSender mailSender = new JavaMailSenderImpl();


    public void sendToMail(String toMail,String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("davidjanssen2001@gmail.com");
        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(body);
        System.out.println("Sending mail");
        
        mailSender.send(message);
        System.out.println("Mail send!");
    }
    public MailService() {
    }

}
