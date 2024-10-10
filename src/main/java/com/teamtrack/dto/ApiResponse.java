package com.teamtrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private boolean success;
    private String token;

    public ApiResponse(String message, boolean success){
        this.message=message;
        this.success=success;
    }

    public ApiResponse(String message){
        this.message=message;
    }
}
