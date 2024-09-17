package com.mylibrary.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylibrary.app.entities.Role;
import com.mylibrary.app.entities.User;
import com.mylibrary.app.enums.EnumRole;
import com.mylibrary.app.repositories.RoleRepo;
import com.mylibrary.app.repositories.UserRepo;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
    
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;


    @PostMapping("")
    public void store(){
        Role role = new Role();
        role.setName(EnumRole.ROLE_ADMIN);
        this.roleRepo.save(role);

        List<User> users = this.userRepo.findAll();
        List<String> rolesList = new ArrayList<>();

        users.forEach(user -> {
            user.getRoles().forEach(r -> {
                rolesList.add(r.getName().toString());
                System.out.println(r.getName().toString());
            });
        });
        

    }

}
