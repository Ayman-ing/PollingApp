package com.example.polls.service;

import com.example.polls.domain.CustomUserDetails;
import com.example.polls.domain.entities.UserEntity;
import com.example.polls.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        UserEntity user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(

               () -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail)
        );
        logger.info("User Authenticated Successfully..!!!");
        return CustomUserDetails.create(user);

    }
}
