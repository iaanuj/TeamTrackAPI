package com.teamtrack.service;

import com.teamtrack.exception.EmailSendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationMail(String toEmail, String confirmationCode) throws EmailSendException {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(toEmail);
            helper.setSubject("TeamTrack - Confirm your account");

            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; text-align: center; padding: 20px; background-color: #eaeaea;'>"
                            + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);'>"
                            + "<h2 style='color: #6A5ACD;'>Welcome to TeamTrack!</h2>"
                            + "<p style='font-size: 18px; color: #555;'>Thank you for signing up! Please use the confirmation code below to complete your registration:</p>"
                            + "<div style='font-size: 32px; font-weight: bold; color: #333; background-color: #f0f0f0; padding: 20px; border-radius: 8px; display: inline-block; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);'>%s</div>"
                            + "<p style='margin-top: 20px; font-size: 14px; color: #777;'>If you didn't request this, feel free to ignore this email.</p>"
                            + "<hr style='border: none; border-top: 1px solid #ddd; margin: 30px 0;'>"
                            + "<p style='font-size: 12px; color: #777;'>"
                            + "Best regards,<br>TeamTrack Support"
                            + "</p>"
                            + "</div>"
                            + "</div>",
                    confirmationCode
            );

            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            log.error("Exception while sendConfirmationMail.", e);
            throw new EmailSendException("Failed to send Confirmation mail");
        }
    }
}
