package com.mylibrary.app.services.Impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mylibrary.app.entities.User;
import com.mylibrary.app.payloads.Requests.RegisterRequest;
import com.mylibrary.app.repositories.UserRepo;
import com.mylibrary.app.services.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;



    @Override
    public User register(RegisterRequest request) {
        User user = this.modelMapper.map(request, User.class);
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());
        user = this.userRepo.save(user);
        return user;
    }

}
