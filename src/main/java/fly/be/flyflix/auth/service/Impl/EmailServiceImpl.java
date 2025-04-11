package fly.be.flyflix.auth.service.Impl;

import fly.be.flyflix.auth.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmail(String destinationEmailAdress, String subject, String body) {
        try {
            // Create a MimeMessage instance
            MimeMessage mimeMessage = emailSender.createMimeMessage();

            // MimeMessageHelper para usar html
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true); // 'true' indicates multipart (for HTML)

            // dados para o email
            messageHelper.setFrom(sender);
            messageHelper.setTo(destinationEmailAdress);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true); // 'true' habilitar HTML content

            // Send the email
            emailSender.send(mimeMessage);

            return "Email sent successfully!";
        } catch (MessagingException e) {
            return "Error sending email: " + e.getMessage();
        }
    }

}
