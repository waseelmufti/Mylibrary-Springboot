package com.mylibrary.app.payloads;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BookDTO {
    private String id;
    @NotBlank
    private String title;
    private String subtitle;
    @NotEmpty
    private List<AuthorDTO> authorsDTO;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
