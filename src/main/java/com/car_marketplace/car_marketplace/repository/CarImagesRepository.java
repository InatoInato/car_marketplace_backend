package com.car_marketplace.car_marketplace.repository;

import com.car_marketplace.car_marketplace.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarImagesRepository extends JpaRepository<CarImage, Long> {
}
