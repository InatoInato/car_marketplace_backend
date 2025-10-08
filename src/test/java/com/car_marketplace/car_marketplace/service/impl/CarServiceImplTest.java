package car_marketplace.car_marketplace.service.impl;

import com.car_marketplace.car_marketplace.dto.CarDto;
import com.car_marketplace.car_marketplace.dto.CreateCarDto;
import com.car_marketplace.car_marketplace.entity.Car;
import com.car_marketplace.car_marketplace.repository.CarRepository;
import com.car_marketplace.car_marketplace.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository repository;

    @InjectMocks
    private CarServiceImpl service;

    @BeforeEach
    void init() {
    }

    @Test
    void createCar_shouldReturnDto() {
        CreateCarDto dto = new CreateCarDto("BMW", "M3", 2020, "Black",
                3.0, 50000, "Fast car");

        Car savedCar = Car.builder()
                .id(1L)
                .make("BMW")
                .model("M3")
                .year(2020)
                .color("Black")
                .engineCapacity(3.0)
                .price(50000)
                .description("Fast car")
                .build();

        when(repository.save(any(Car.class))).thenReturn(savedCar);

        CarDto result = service.createCar(dto);

        assertEquals("BMW", result.make());
        assertEquals("M3", result.model());
        assertEquals(50000, result.price());
    }
}
