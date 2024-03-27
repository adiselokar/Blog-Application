package com.blog.services;

import com.blog.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto user, long userId);
    UserDto getUserById(long userId);
    List<UserDto> getAllUsers();
    void deleteUser(long userId);

}
