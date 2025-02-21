package com.teamtrack.entity;

import com.teamtrack.enumModel.InvitationStatus;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "invitation")
public class Invitation {
    @Id
    private ObjectId invitationId;
    private ObjectId groupId;
    private String invitedUserName;
    private String inviterUserName;
    private InvitationStatus status;
    private LocalDateTime createdAt;
}
