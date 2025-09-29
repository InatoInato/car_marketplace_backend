package com.car_marketplace.car_marketplace.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateCarDto(
        @NotBlank(message = "Make must not be empty")
        String make,


        @NotBlank(message = "Model must not be empty")
        String model,

        @Min(value = 1886, message = "Year must be realistic")
        @Max(value = 2050, message = "Year must be realistic")
        int year,

        @NotBlank(message = "Color must not be empty")
        String color,

        @Positive(message = "Engine capacity must be positive")
        double engineCapacity,

        @Positive(message = "Price must be positive")
        double price,

        @NotBlank(message = "Description must not be empty")
        String description
) {}
