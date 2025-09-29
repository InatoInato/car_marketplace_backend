package com.car_marketplace.car_marketplace.service.impl;

import com.car_marketplace.car_marketplace.dto.CarDto;
import com.car_marketplace.car_marketplace.dto.CarMapper;
import com.car_marketplace.car_marketplace.dto.CreateCarDto;
import com.car_marketplace.car_marketplace.entity.Car;
import com.car_marketplace.car_marketplace.exception.CarNotFoundException;
import com.car_marketplace.car_marketplace.repository.CarRepository;
import com.car_marketplace.car_marketplace.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository repository;

    @Override
    public CarDto createCar(CreateCarDto carDto) {
        Car car = CarMapper.toEntity(carDto);
        Car savedCar = repository.save(car);
        return CarMapper.toDto(savedCar);
    }

    @Override
    public CarDto getCarById(Long id) {
        return repository.findById(id)
                .map(CarMapper::toDto)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @Override
    public List<CarDto> getAllCars() {
        return repository.findAll().stream()
                .map(CarMapper::toDto)
                .toList();
    }

    @Override
    public CarDto updateCar(Long id, CarDto carDto) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        car.setMake(carDto.make());
        car.setModel(carDto.model());
        car.setYear(carDto.year());
        car.setColor(carDto.color());
        car.setEngineCapacity(carDto.engineCapacity());
        car.setPrice(carDto.price());
        car.setDescription(carDto.description());
        Car updatedCar = repository.save(car);
        return CarMapper.toDto(updatedCar);
    }

    @Override
    public CarDto updateCarPrice(Long id, CarDto carDto) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));

        car.setPrice(carDto.price());
        Car updatedCar = repository.save(car);
        return CarMapper.toDto(updatedCar);
    }

    @Override
    public void deleteCar(Long id) {
        if(!repository.existsById(id)) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        repository.deleteById(id);
    }


}
