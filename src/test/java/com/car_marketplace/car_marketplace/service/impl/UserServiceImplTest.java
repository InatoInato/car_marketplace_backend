package car_marketplace.car_marketplace.service.impl;

import com.car_marketplace.car_marketplace.config.JwtUtil;
import com.car_marketplace.car_marketplace.dto.CreateUserDto;
import com.car_marketplace.car_marketplace.dto.UserDto;
import com.car_marketplace.car_marketplace.dto.login.LoginRequest;
import com.car_marketplace.car_marketplace.dto.login.LoginResponse;
import com.car_marketplace.car_marketplace.dto.mapper.UserMapper;
import com.car_marketplace.car_marketplace.entity.User;
import com.car_marketplace.car_marketplace.exception.ResourceAlreadyExistException;
import com.car_marketplace.car_marketplace.exception.UserNotFoundException;
import com.car_marketplace.car_marketplace.repository.UserRepository;
import com.car_marketplace.car_marketplace.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private CreateUserDto createUserDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .username("john_doe")
                .email("john@example.com")
                .password("encodedPass")
                .phoneNumber("1234567890")
                .role("USER")
                .build();

        userDto = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );

        createUserDto = new CreateUserDto(
                "john_doe",
                "john@example.com",
                "password123",
                "1234567890"
        );
    }

    // ---------- createUser ----------
    @Test
    void createUser_ShouldSaveUser_WhenValid() {
        when(userRepository.existsByUsername(createUserDto.username())).thenReturn(false);
        when(userRepository.existsByEmail(createUserDto.email())).thenReturn(false);
        when(userMapper.toEntity(createUserDto)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.createUser(createUserDto);

        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("john_doe");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrow_WhenUsernameExists() {
        when(userRepository.existsByUsername(createUserDto.username())).thenReturn(true);
        assertThatThrownBy(() -> userService.createUser(createUserDto))
                .isInstanceOf(ResourceAlreadyExistException.class)
                .hasMessageContaining("Username already exist");
    }

    @Test
    void createUser_ShouldThrow_WhenEmailExists() {
        when(userRepository.existsByUsername(createUserDto.username())).thenReturn(false);
        when(userRepository.existsByEmail(createUserDto.email())).thenReturn(true);
        assertThatThrownBy(() -> userService.createUser(createUserDto))
                .isInstanceOf(ResourceAlreadyExistException.class)
                .hasMessageContaining("Email already exist");
    }

    // ---------- getAllUsers ----------
    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        List<UserDto> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).email()).isEqualTo("john@example.com");
    }

    // ---------- getUserById ----------
    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getUserById(1L);

        assertThat(result.username()).isEqualTo("john_doe");
    }

    @Test
    void getUserById_ShouldThrow_WhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(UserNotFoundException.class);
    }

    // ---------- deleteUser ----------
    @Test
    void deleteUser_ShouldDelete_WhenExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_ShouldThrow_WhenNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(UserNotFoundException.class);
    }

    // ---------- login ----------
    @Test
    void login_ShouldReturnToken_WhenValidCredentials() {
        LoginRequest loginRequest = new LoginRequest("john@example.com", "password123");

        when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(user.getEmail())).thenReturn("fake-jwt-token");

        LoginResponse response = userService.login(loginRequest);

        assertThat(response.message()).isEqualTo("Login successful");
        assertThat(response.token()).isEqualTo("fake-jwt-token");
    }

    @Test
    void login_ShouldThrow_WhenEmailInvalid() {
        LoginRequest loginRequest = new LoginRequest("wrong@example.com", "password");
        when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid email or password");
    }

    @Test
    void login_ShouldThrow_WhenPasswordInvalid() {
        LoginRequest loginRequest = new LoginRequest("john@example.com", "wrongpass");
        when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.password(), user.getPassword())).thenReturn(false);
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid email or password");
    }
}
