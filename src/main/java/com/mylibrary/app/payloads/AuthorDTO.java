package com.mylibrary.app.payloads;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorDTO {
    private String id;
    @NotBlank
    private String name;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
