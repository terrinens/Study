package com.example.jwt.service;

import com.example.jwt.dto.UserDTO;
import com.example.jwt.entity.Authority;
import com.example.jwt.entity.User;
import com.example.jwt.role.UserRole;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDTO userDTO) {
        if (userRepository
                .findOneWithAuthoritiesByUsername(userDTO.username())
                .orElse(null) != null
        ) {
            throw new RuntimeException("이미 가입되어 있는 유저 입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName(UserRole.USER)
                .build();

        User user = User.builder()
                .username(userDTO.username())
                .password(passwordEncoder.encode(userDTO.password()))
                .nickname(userDTO.nickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}
