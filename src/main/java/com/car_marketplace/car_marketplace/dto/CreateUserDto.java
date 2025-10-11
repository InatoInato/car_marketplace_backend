package com.car_marketplace.car_marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank(message = "Username must not be empty")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Phone number must not be empty")
        @Pattern(regexp = "\\+\\d{10,15}", message = "Invalid phone number format")
        String phoneNumber,

        @NotBlank(message = "Password must not be empty")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
) {
}
