package com.mirea.hardwarestore.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mirea.hardwarestore.model.user.User;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String phone;
    private String email;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setBirthday(user.getBirthday());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
