package com.mirea.hardwarestore.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mirea.hardwarestore.model.user.Role;
import com.mirea.hardwarestore.model.user.Status;
import com.mirea.hardwarestore.model.user.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String phone;
    private String email;
    private String status;
    private String role;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));

        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setFirstName(user.getFirstName());
        adminUserDto.setLastName(user.getLastName());
        adminUserDto.setBirthday(user.getBirthday());
        adminUserDto.setPhone(user.getPhone());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setStatus(user.getStatus().name());

        List<Role> roles = user.getRoles();
        roles.forEach(role -> {
            if (role.getName().equals("ROLE_ADMIN")) {
                adminUserDto.setRole("ADMIN");
            }
            else {
                adminUserDto.setRole("USER");
            }
        });

        return adminUserDto;
    }
}
