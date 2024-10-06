package com.teamtrack.schedular;

import com.teamtrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserSchedule {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 0 1/1 * ? *")
    public void cleanUpUnverifiedUsers(){
        userService.deleteUnverifiedUsers();
    }
}
