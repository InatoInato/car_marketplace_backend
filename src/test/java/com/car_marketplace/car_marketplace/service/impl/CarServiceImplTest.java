package car_marketplace.car_marketplace.service.impl;

import com.car_marketplace.car_marketplace.dto.CarDto;
import com.car_marketplace.car_marketplace.dto.CreateCarDto;
import com.car_marketplace.car_marketplace.entity.Car;
import com.car_marketplace.car_marketplace.entity.User;
import com.car_marketplace.car_marketplace.repository.CarRepository;
import com.car_marketplace.car_marketplace.repository.UserRepository;
import com.car_marketplace.car_marketplace.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .username("Test User")
                .email("test@example.com")
                .password("123456")
                .role("USER")
                .build();

        car = Car.builder()
                .id(1L)
                .make("Toyota")
                .model("Camry")
                .year(2020)
                .color("Black")
                .engineCapacity(2.5)
                .price(20000)
                .description("Reliable sedan")
                .user(user)
                .build();
    }

    @Test
    void createCar_ShouldReturnCreatedCarDto() {
        CreateCarDto createCarDto = new CreateCarDto("Toyota", "Camry", 2020, "Black", 2.5, 20000, "Reliable sedan");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        CarDto result = carService.createCar(createCarDto);

        assertThat(result).isNotNull();
        assertThat(result.make()).isEqualTo("Toyota");
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void getCarById_ShouldReturnCarDto() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        CarDto result = carService.getCarById(1L);

        assertThat(result).isNotNull();
        assertThat(result.model()).isEqualTo("Camry");
    }

    @Test
    void getAllCars_ShouldReturnList() {
        when(carRepository.findAll()).thenReturn(List.of(car));

        List<CarDto> result = carService.getAllCars();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).make()).isEqualTo("Toyota");
    }

    @Test
    void updateCar_ShouldReturnUpdatedCarDto() {
        Car updatedCar = Car.builder()
                .id(1L)
                .make("Toyota")
                .model("Camry")
                .year(2021)
                .color("White")
                .engineCapacity(2.5)
                .price(22000)
                .description("Updated car")
                .user(user)
                .build();

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        CarDto dto = new CarDto(1L, "Toyota", "Camry", 2021, "White", 2.5, 22000, "Updated car");
        CarDto result = carService.updateCar(1L, dto);

        assertThat(result).isNotNull();
        assertThat(result.color()).isEqualTo("White");
        assertThat(result.price()).isEqualTo(22000);
    }

    @Test
    void updateCarPrice_ShouldReturnUpdatedCarDto() {
        Car updatedCar = Car.builder()
                .id(1L)
                .make("Toyota")
                .model("Camry")
                .year(2020)
                .color("Black")
                .engineCapacity(2.5)
                .price(25000)
                .description("Price updated")
                .user(user)
                .build();

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        CarDto priceDto = new CarDto(1L, "Toyota", "Camry", 2020, "Black", 2.5, 25000, "Price updated");
        CarDto result = carService.updateCarPrice(1L, priceDto);

        assertThat(result.price()).isEqualTo(25000);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void deleteCar_ShouldInvokeRepositoryDelete() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        doNothing().when(carRepository).delete(car);

        carService.deleteCar(1L);

        verify(carRepository, times(1)).delete(car);
    }
}
