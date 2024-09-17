package com.mylibrary.app.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylibrary.app.config.security.JWTService;
import com.mylibrary.app.exceptions.ResourceNotFoundException;
import com.mylibrary.app.payloads.Requests.LoginRequest;
import com.mylibrary.app.payloads.Response.LoginResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/login")
public class AuthController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws Exception{
        this.authenticate(request.getEmail(), request.getPassword());
        System.out.println(request.getEmail() + " " + request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtService.generateToken(userDetails);

        return new ResponseEntity<LoginResponse>(new LoginResponse(token, "login_success", "Logged in successfully"), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception{
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ResourceNotFoundException("login_invalid");
        }catch(Exception e){
            throw new Exception(e);
        }
    }

}
