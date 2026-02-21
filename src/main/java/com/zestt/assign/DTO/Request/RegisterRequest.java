package com.zestt.assign.DTO.Request;



import com.zestt.assign.Entity.Role;

import lombok.Data;

@Data
public class RegisterRequest {
  private String userName;
    private String password;
    private Role role; // enum
}
