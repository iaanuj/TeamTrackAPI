package com.teamtrack.dto;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;

@Data
public class InvitationRequest {
    @NonNull
    private ObjectId groupId;

    @NonNull
    private String invitedUserName;
}