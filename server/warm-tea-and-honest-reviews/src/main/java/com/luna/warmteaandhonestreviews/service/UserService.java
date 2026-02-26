package com.luna.warmteaandhonestreviews.service;

import com.luna.warmteaandhonestreviews.domain.UserEntity;
import com.luna.warmteaandhonestreviews.exception.UserNotFoundException;
import com.luna.warmteaandhonestreviews.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String getUserIdByUsername(@NonNull String username) {
        return userRepository.findByUsername(username)
            .map(UserEntity::getId)
            .orElseThrow(
                () -> new UserNotFoundException("User not found with username: " + username));
    }
}
