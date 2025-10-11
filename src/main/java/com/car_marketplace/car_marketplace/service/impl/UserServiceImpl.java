package com.car_marketplace.car_marketplace.service.impl;

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
import com.car_marketplace.car_marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserDto createUser(CreateUserDto dto) {
        if(userRepository.existsByUsername(dto.username())){
            throw new ResourceAlreadyExistException("Username already exist");
        }

        if(userRepository.existsByEmail(dto.email())){
            throw new ResourceAlreadyExistException("Email already exist");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        } else {
            userRepository.deleteById(id);
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse("Login successful", token);
    }
}
