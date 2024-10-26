package com.mylibrary.app.payloads;

import java.time.LocalDateTime;

import com.mylibrary.app.validations.AllowedValues;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorDTO {
    private String id;
    @NotBlank
    private String name;
    @AllowedValues(value = {"true", "false"}, targetType = Boolean.class, message = "Status must be true or false")
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
