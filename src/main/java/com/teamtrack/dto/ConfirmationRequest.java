package com.teamtrack.dto;

import lombok.Data;

@Data
public class ConfirmationRequest {
    private String email;
    private String confirmationCode;
}
