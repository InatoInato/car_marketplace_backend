package com.car_marketplace.car_marketplace.dto.mapper;

import com.car_marketplace.car_marketplace.dto.*;
import com.car_marketplace.car_marketplace.entity.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CarMapper {

    public static CarDto toDto(Car car) {
        List<CarImageDto> imageDto = car.getImages() != null
                ? car.getImages().stream()
                .map(img -> new CarImageDto(img.getId(), img.getImageUrl()))
                .toList()
                : List.of();

        return new CarDto(
                car.getId(),
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getEngineCapacity(),
                car.getPrice(),
                car.getDescription(),
                imageDto
        );
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
