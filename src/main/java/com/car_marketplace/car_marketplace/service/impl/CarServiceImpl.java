package com.car_marketplace.car_marketplace.service.impl;

import com.car_marketplace.car_marketplace.dto.CarDto;
import com.car_marketplace.car_marketplace.dto.mapper.CarMapper;
import com.car_marketplace.car_marketplace.dto.CreateCarDto;
import com.car_marketplace.car_marketplace.entity.Car;
import com.car_marketplace.car_marketplace.entity.CarImage;
import com.car_marketplace.car_marketplace.entity.User;
import com.car_marketplace.car_marketplace.exception.CarNotFoundException;
import com.car_marketplace.car_marketplace.exception.UserNotFoundException;
import com.car_marketplace.car_marketplace.repository.CarImagesRepository;
import com.car_marketplace.car_marketplace.repository.CarRepository;
import com.car_marketplace.car_marketplace.repository.UserRepository;
import com.car_marketplace.car_marketplace.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository repository;
    private final UserRepository userRepository;
    private final CarImagesRepository carImagesRepository;

    @Override
    public CarDto createCar(CreateCarDto carDto) {
        User user = userRepository.findById(carDto.userId())
                .orElseThrow(() -> new UserNotFoundException(carDto.userId()));

        Car car = Car.builder()
                .make(carDto.make())
                .model(carDto.model())
                .year(carDto.year())
                .color(carDto.color())
                .engineCapacity(carDto.engineCapacity())
                .price(carDto.price())
                .description(carDto.description())
                .user(user)
                .build();

        Car savedCar = repository.save(car);
        return CarMapper.toDto(savedCar);
    }

    @Override
    public CarDto addCarImages(Long id, List<String> imageUrls) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));

        List<CarImage> images = imageUrls.stream()
                .map(url -> {
                    CarImage carImage = new CarImage();
                    carImage.setImageUrl(url);
                    carImage.setCar(car);
                    return carImage;
                })
                .toList();

        carImagesRepository.saveAll(images);
        car.getImages().addAll(images);
        repository.save(car);

        return CarMapper.toDto(car);
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
