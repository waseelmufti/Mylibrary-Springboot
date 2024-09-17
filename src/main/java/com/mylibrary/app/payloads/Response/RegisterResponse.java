package com.mylibrary.app.payloads.Response;

import com.mylibrary.app.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private String status;
    private User user;
}
