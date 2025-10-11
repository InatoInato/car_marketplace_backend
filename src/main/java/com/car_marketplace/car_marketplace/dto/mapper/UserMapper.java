package com.car_marketplace.car_marketplace.dto.mapper;

import com.car_marketplace.car_marketplace.dto.CreateUserDto;
import com.car_marketplace.car_marketplace.dto.UserDto;
import com.car_marketplace.car_marketplace.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    public User toEntity(CreateUserDto dto){
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPhoneNumber(dto.phoneNumber());
        user.setRole("USER"); // Default role
        return user;
    }
}
