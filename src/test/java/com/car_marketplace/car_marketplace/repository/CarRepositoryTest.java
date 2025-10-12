package car_marketplace.car_marketplace.repository;

import com.car_marketplace.car_marketplace.entity.Car;
import com.car_marketplace.car_marketplace.entity.User;
import com.car_marketplace.car_marketplace.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    private Car car1;
    private Car car2;

    private User user;

    @BeforeEach
    void setUp() {
        // можно сделать фиктивного юзера, чтобы не было null constraint на user_id
        user = User.builder()
                .id(1L)
                .username("Test User")
                .email("test@example.com")
                .password("123456")
                .role("USER")
                .build();

        car1 = new Car(null, "Toyota", "Camry", 2020,
                "Black", 2.5, 20000,
                "Reliable sedan", user);

        car2 = new Car(null, "Honda", "Civic", 2018,
                "Blue", 2.0, 15000,
                "Compact car", user);

        carRepository.saveAll(List.of(car1, car2));
    }

    @Test
    void findAll_ShouldReturnCars() {
        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(2);
    }

    @Test
    void findById_ShouldReturnCar() {
        Optional<Car> found = carRepository.findById(car1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getMake()).isEqualTo("Toyota");
    }

    @Test
    void save_ShouldPersistCar() {
        Car car = new Car(null, "BMW", "X5", 2021,
                "White", 3.0, 50000,
                "Luxury SUV", user);

        Car saved = carRepository.save(car);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getMake()).isEqualTo("BMW");
    }

    @Test
    void deleteById_ShouldRemoveCar() {
        carRepository.deleteById(car1.getId());
        assertThat(carRepository.findById(car1.getId())).isEmpty();
    }
}
