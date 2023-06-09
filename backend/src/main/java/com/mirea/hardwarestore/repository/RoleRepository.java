package com.mirea.hardwarestore.repository;

import com.mirea.hardwarestore.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
