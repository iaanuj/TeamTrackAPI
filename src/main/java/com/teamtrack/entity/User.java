package com.teamtrack.entity;



import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    @NotBlank
    @Size(min = 3, max = 15)
    private String userName;

    @NonNull
    @NotBlank
    @Size(min = 3, max = 15)
    private String userPassword;

    @Indexed(unique = true)
    @NonNull
    @NotBlank
    private String userMail;

    private String userOtp;
    private boolean active;
    private LocalDateTime registrationDate;

    private List<String> roles;
}
