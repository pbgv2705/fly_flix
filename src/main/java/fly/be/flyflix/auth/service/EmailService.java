package fly.be.flyflix.auth.service;

public interface EmailService {
    String sendEmail(String destinationEmailAdress, String subject, String body);
}