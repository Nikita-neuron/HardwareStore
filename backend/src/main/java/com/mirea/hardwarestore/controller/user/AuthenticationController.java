package com.mirea.hardwarestore.controller.user;

import com.mirea.hardwarestore.dto.user.AuthenticationRequestDto;
import com.mirea.hardwarestore.dto.user.RegisterRequestDto;
import com.mirea.hardwarestore.model.user.Role;
import com.mirea.hardwarestore.model.user.User;
import com.mirea.hardwarestore.security.jwt.JwtTokenProvider;
import com.mirea.hardwarestore.service.UserAlreadyExistAuthenticationException;
import com.mirea.hardwarestore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/auth/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            String password = requestDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userService.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("User with email: " + email + " not found");
            }

            String token = jwtTokenProvider.createToken(email, user.getRoles());

            Map<String, String> response = new HashMap<>();
            response.put("email", email);
            response.put("firstName", user.getFirstName());
            List<Role> roles = user.getRoles();
            roles.forEach(role -> {
                if (role.getName().equals("ROLE_ADMIN")) {
                    response.put("role", "ADMIN");
                }
                else {
                    response.put("role", "USER");
                }
            });
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto requestDto) {
        try {
            User user = new User();
            user.setFirstName(requestDto.getFirstName());
            user.setLastName(requestDto.getLastName());
            user.setBirthday(requestDto.getBirthday());
            user.setPhone(requestDto.getPhone());
            user.setEmail(requestDto.getEmail());
            user.setPassword(requestDto.getPassword());

            userService.register(user);

            String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());

            Map<String, String> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("firstName", user.getFirstName());
            List<Role> roles = user.getRoles();
            roles.forEach(role -> {
                if (role.getName().equals("ROLE_ADMIN")) {
                    response.put("role", "ADMIN");
                }
                else {
                    response.put("role", "USER");
                }
            });
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        catch (UserAlreadyExistAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
