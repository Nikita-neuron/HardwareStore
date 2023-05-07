package com.mirea.hardwarestore.controller.user;

import com.mirea.hardwarestore.dto.user.UserDto;
import com.mirea.hardwarestore.model.user.User;
import com.mirea.hardwarestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
