package com.example.jwt.controller;

import com.example.jwt.dto.UserDTO;
import com.example.jwt.entity.User;
import com.example.jwt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.signup(userDTO));
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getMyUserInfo() {
        return userService.getMyWithAuthorities()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        log.info("어드민 유저 정보 발송");
        return userService.getUserWithAuthorities(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
