package com.example.jwt;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("유저 더미")
    @Test
    void userDummy() {
        userRepository.save(
                User.builder()
                .activated(true)
                .username("test1")
                .password(passwordEncoder.encode("1234"))
                .nickname("test1")
                .build()
        );
    }
}
