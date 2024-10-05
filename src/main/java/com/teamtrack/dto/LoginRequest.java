package com.teamtrack.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequest {
    @NonNull
    private String userName;

    @NonNull
    private String userPassword;
}
