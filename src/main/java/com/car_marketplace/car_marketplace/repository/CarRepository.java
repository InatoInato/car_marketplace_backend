package com.car_marketplace.car_marketplace.repository;

import com.car_marketplace.car_marketplace.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
