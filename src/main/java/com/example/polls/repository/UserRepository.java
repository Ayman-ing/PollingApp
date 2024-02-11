package com.example.polls.repository;

import com.example.polls.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsernameOrEmail(String username,String email);
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findByIdIn(List<Long> userIds);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


}
