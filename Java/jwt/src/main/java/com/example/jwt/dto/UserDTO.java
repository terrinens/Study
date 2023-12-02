package com.example.jwt.dto;

public record UserDTO (
        String username,
        String password,
        String nickname
) {
}
