package com.teamtrack.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationMail(String toEmail, String confirmationCode){
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(toEmail);
            mail.setSubject("Your TeamTrack confirmation code!!");
            mail.setText("YOUR CONFIRMATION CODE IS : " + confirmationCode);
            javaMailSender.send(mail);
        }catch (Exception e){
            log.error("Exception while sendConfirmationMail.", e);
        }
    }
}
