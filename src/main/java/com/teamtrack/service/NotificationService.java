package com.teamtrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    public void sendInvitationNotification(String userName, String message){
        String destination = "/topic/invites/" + userName;
        messagingTemplate.convertAndSend(destination, message);
    }
}
