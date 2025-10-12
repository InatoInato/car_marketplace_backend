package car_marketplace.car_marketplace.repository;

import com.car_marketplace.car_marketplace.entity.User;
import com.car_marketplace.car_marketplace.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUserExists() {
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .phoneNumber("1234567890")
                .password("pass")
                .role("USER")
                .build();

        userRepository.save(user);

        assertThat(userRepository.existsByUsername("testuser")).isTrue();
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenUserExists() {
        User user = User.builder()
                .username("user123")
                .email("mail@mail.com")
                .phoneNumber("1234567890")
                .password("pass")
                .role("USER")
                .build();

        userRepository.save(user);

        assertThat(userRepository.existsByEmail("mail@mail.com")).isTrue();
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenEmailExists() {
        User user = User.builder()
                .username("alpha")
                .email("alpha@example.com")
                .phoneNumber("7777777777")
                .password("pass")
                .role("ADMIN")
                .build();

        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("alpha@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("alpha");
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenEmailNotFound() {
        Optional<User> found = userRepository.findByEmail("no@exists.com");
        assertThat(found).isEmpty();
    }
}
