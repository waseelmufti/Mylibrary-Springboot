package com.mylibrary.app.services;

import com.mylibrary.app.entities.User;
import com.mylibrary.app.payloads.Requests.RegisterRequest;

public interface UserService {
    public User register(RegisterRequest request);
}
