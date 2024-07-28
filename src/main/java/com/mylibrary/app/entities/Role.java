package com.mylibrary.app.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mylibrary.app.enums.EnumRole;

import lombok.Data;

@Data
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private EnumRole name;

}
