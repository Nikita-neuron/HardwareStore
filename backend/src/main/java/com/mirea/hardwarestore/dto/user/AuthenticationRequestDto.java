package com.mirea.hardwarestore.dto.user;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String email;
    private String password;
}
