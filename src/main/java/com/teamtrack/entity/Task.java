package com.teamtrack.entity;



import com.teamtrack.enumModel.TaskStatus;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import java.time.LocalDateTime;

@Document(collection = "tasks")
@Data
public class Task {
    @Id
    private ObjectId taskId;

    private ObjectId groupId;

    private String assignedToUserName;

    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private TaskStatus taskStatus;

}
