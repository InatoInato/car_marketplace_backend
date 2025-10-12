package car_marketplace.car_marketplace.controller;

import com.car_marketplace.car_marketplace.controller.UserController;
import com.car_marketplace.car_marketplace.dto.CreateUserDto;
import com.car_marketplace.car_marketplace.dto.UserDto;
import com.car_marketplace.car_marketplace.dto.login.LoginRequest;
import com.car_marketplace.car_marketplace.dto.login.LoginResponse;
import com.car_marketplace.car_marketplace.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_ShouldReturnUserDto() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto(
                "john_doe", "john@example.com", "password", "1234567890"
        );

        UserDto responseDto = new UserDto(1L, "john_doe", "john@example.com", "1234567890", "USER");

        Mockito.when(userService.createUser(any(CreateUserDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        UserDto userDto = new UserDto(1L, "john", "john@example.com", "9999999999", "USER");
        Mockito.when(userService.getUserById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getAllUsers_ShouldReturnList() throws Exception {
        List<UserDto> users = List.of(
                new UserDto(1L, "a", "a@mail.com", "123", "USER"),
                new UserDto(2L, "b", "b@mail.com", "456", "ADMIN")
        );

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("a"))
                .andExpect(jsonPath("$[1].role").value("ADMIN"));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).deleteUser(1L);
    }

    @Test
    void login_ShouldReturnToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest("john@example.com", "password");
        LoginResponse loginResponse = new LoginResponse("Login successful", "jwt-token-123");

        Mockito.when(userService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-123"));
    }
}
