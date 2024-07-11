package com.example.demo_app.mapper;

import com.example.demo_app.dto.UserDto;
import com.example.demo_app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    List<UserDto> usersToUserDtos(List<User> users);
}
