package com.car_marketplace.car_marketplace.dto;

import com.car_marketplace.car_marketplace.entity.Car;

public class CarMapper {
    public static CarDto toDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getEngineCapacity(),
                car.getPrice(),
                car.getDescription()
        );
    }

    public static Car toEntity(CarDto dto) {
        return Car.builder()
                .id(dto.id())
                .make(dto.make())
                .model(dto.model())
                .year(dto.year())
                .color(dto.color())
                .engineCapacity(dto.engineCapacity())
                .price(dto.price())
                .description(dto.description())
                .build();
    }

    public static Car toEntity(CreateCarDto dto) {
        return Car.builder()
                .make(dto.make())
                .model(dto.model())
                .year(dto.year())
                .color(dto.color())
                .engineCapacity(dto.engineCapacity())
                .price(dto.price())
                .description(dto.description())
                .build();
    }
}
