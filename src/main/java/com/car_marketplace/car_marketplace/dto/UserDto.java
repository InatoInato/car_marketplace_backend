package com.car_marketplace.car_marketplace.dto;

public record UserDto(
        Long id,
        String username,
        String email,
        String phoneNumber,
        String role
) {
}
