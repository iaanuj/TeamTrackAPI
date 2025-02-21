package com.teamtrack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    private ObjectId groupId;
    @NonNull
    private String groupName;
    @NonNull
    private String groupDescription;

    private List<Member> groupMembers;

    private List<String> groupAdmins;

}
