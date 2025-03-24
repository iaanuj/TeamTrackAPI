package com.teamtrack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    public void sendNotification(String userName, String message){
        log.info("Sending notification to user: {} with message: {}", userName, message);
        messagingTemplate.convertAndSendToUser(userName, "notification", message);
    }
}
