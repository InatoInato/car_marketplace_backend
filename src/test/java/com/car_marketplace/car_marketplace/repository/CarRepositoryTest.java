package car_marketplace.car_marketplace.repository;

import com.car_marketplace.car_marketplace.entity.Car;
import com.car_marketplace.car_marketplace.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private CarRepository repository;

    @Test
    void shouldSaveAndFindCar() {
        Car car = Car.builder()
                .make("Toyota")
                .model("Camry")
                .year(2015)
                .color("White")
                .engineCapacity(2.5)
                .price(20000)
                .description("Reliable car")
                .build();

        Car saved = repository.save(car);
        Car found = repository.findById(saved.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getMake()).isEqualTo("Toyota");
        assertThat(found.getPrice()).isEqualTo(20000);
    }
}
