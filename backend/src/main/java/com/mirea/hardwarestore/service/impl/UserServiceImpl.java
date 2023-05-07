package com.mirea.hardwarestore.service.impl;

import com.mirea.hardwarestore.model.user.Role;
import com.mirea.hardwarestore.model.user.Status;
import com.mirea.hardwarestore.model.user.User;
import com.mirea.hardwarestore.repository.RoleRepository;
import com.mirea.hardwarestore.repository.UserRepository;
import com.mirea.hardwarestore.service.UserAlreadyExistAuthenticationException;
import com.mirea.hardwarestore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) throws UserAlreadyExistAuthenticationException{
        if (userRepository.findByEmail(user.getEmail()) != null) {
            log.warn("IN register - user with email: {} already exist", user.getEmail());
            throw new UserAlreadyExistAuthenticationException("User with email: " + user.getEmail() + " already exist");
        }

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        user.setCreated(new Date());
        user.setUpdated(new Date());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN findByID - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email);

        if (result == null) {
            log.warn("IN findByEmail - no user found by email: {}", email);
            return null;
        }
        log.info("IN findByEmail - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
