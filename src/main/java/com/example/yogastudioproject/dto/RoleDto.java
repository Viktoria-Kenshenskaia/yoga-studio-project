package com.example.yogastudioproject.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private long roleId;
    private String roleName;
}
