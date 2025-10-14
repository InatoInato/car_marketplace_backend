package com.car_marketplace.car_marketplace.controller;

import com.car_marketplace.car_marketplace.dto.CarDto;
import com.car_marketplace.car_marketplace.dto.CreateCarDto;
import com.car_marketplace.car_marketplace.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CreateCarDto carDto) {
        CarDto createdCar = carService.createCar(carDto);
        return ResponseEntity.ok(createdCar);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<CarDto> addCarImages(
            @PathVariable Long id,
            @RequestBody List<String> imageUrls
    ) {
        CarDto updatedCar = carService.addCarImages(id, imageUrls);
        return ResponseEntity.ok(updatedCar);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        CarDto car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @Valid @RequestBody CarDto carDto) {
        CarDto updatedCar = carService.updateCar(id, carDto);
        return ResponseEntity.ok(updatedCar);
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<CarDto> updateCarPrice(@PathVariable Long id, @Valid @RequestBody CarDto carDto) {
        CarDto updatedCar = carService.updateCarPrice(id, carDto);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
