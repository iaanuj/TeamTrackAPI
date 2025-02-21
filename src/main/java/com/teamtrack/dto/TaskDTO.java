package com.teamtrack.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TaskDTO {
    private ObjectId groupId;
    private String title;
    private String description;
    private String assignedToUserName;
    private LocalDateTime deadline;
}
