package com.example.polls.repository;

import com.example.polls.domain.entities.RoleEntity;
import com.example.polls.domain.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findRoleEntityByName(RoleName roleName);
}
