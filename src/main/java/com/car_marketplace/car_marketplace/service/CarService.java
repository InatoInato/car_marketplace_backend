package com.car_marketplace.car_marketplace.service;

import com.car_marketplace.car_marketplace.dto.CarDto;
import com.car_marketplace.car_marketplace.dto.CreateCarDto;

import java.util.List;

public interface CarService {
    CarDto createCar(CreateCarDto carDto);
    CarDto getCarById(Long id);
    List<CarDto> getAllCars();
    CarDto updateCar(Long id, CarDto carDto);
    CarDto updateCarPrice(Long id, CarDto carDto);
    void deleteCar(Long id);
}
