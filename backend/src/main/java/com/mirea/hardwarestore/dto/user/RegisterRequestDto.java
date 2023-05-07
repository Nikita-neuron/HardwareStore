package com.mirea.hardwarestore.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequestDto {
    private String firstName;
    private String lastName;
    private Date birthday;
    private String phone;
    private String email;
    private String password;
}
