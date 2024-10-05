package com.teamtrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserNameUpdateRequest {
    @NonNull
    @NotBlank
    @Size(min = 3, max = 15)
    private String newUserName;
}
