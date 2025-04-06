package fly.be.flyflix.auth.service.Impl;

import fly.be.flyflix.auth.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmail(String destinationEmailAdress, String subject, String body) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(destinationEmailAdress);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        emailSender.send(mailMessage);
        return "Email sent successfully!";


    }

}
