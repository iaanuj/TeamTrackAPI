package com.teamtrack.entity;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Member {
    private String userName;
    private String groupRole;
}
