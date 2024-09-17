package com.mylibrary.app.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylibrary.app.entities.User;
import com.mylibrary.app.payloads.Requests.RegisterRequest;
import com.mylibrary.app.payloads.Response.RegisterResponse;
import com.mylibrary.app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = this.userService.register(request);
        return new ResponseEntity<RegisterResponse>(new RegisterResponse("created", "OK", user), HttpStatus.OK);
    }
}
