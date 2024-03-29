package com.example.polls.repository;

import com.example.polls.domain.entities.RoleEntity;
import com.example.polls.domain.entities.RoleName;
import com.example.polls.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
@Repository
public interface RoleRepository  extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findRoleEntityByName(RoleName roleName);


    RoleEntity findByName(RoleName roleName);
}
