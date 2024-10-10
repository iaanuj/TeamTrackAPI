package com.teamtrack.dto;

import lombok.Data;

@Data
public class ConfirmationRequest {
    private String userMail;
    private String userOtp;
}
