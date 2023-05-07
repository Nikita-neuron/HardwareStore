package com.mirea.hardwarestore.controller.user;

import com.mirea.hardwarestore.dto.user.AdminUserDto;
import com.mirea.hardwarestore.model.user.User;
import com.mirea.hardwarestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<AdminUserDto>> getUsers() {
        List<User> users = userService.getAll();
        List<AdminUserDto> result = new ArrayList<>();

        users.forEach(user -> result.add(AdminUserDto.fromUser(user)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "users/{id}")
    public void deleteUserById(@PathVariable(name = "id") Long id) {
        userService.delete(id);
    }
}
