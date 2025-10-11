package com.car_marketplace.car_marketplace.service;

import com.car_marketplace.car_marketplace.dto.CreateUserDto;
import com.car_marketplace.car_marketplace.dto.UserDto;
import com.car_marketplace.car_marketplace.dto.login.LoginRequest;
import com.car_marketplace.car_marketplace.dto.login.LoginResponse;

import java.util.List;

public interface UserService {
    UserDto createUser(CreateUserDto dto);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    void deleteUser(Long id);
    LoginResponse login(LoginRequest request);
}
