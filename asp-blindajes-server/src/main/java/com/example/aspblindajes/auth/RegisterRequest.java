package com.example.aspblindajes.auth;

import com.example.aspblindajes.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
