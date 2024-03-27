package com.blog.services.impl;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourseNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        Role role = roleRepository.findById(AppConstants.NORMAL_USER).get();
        User user = dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser( UserDto userDto, long userId ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException("User Not found With Id : " + userId, HttpStatus.NOT_FOUND));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User savedUser = userRepository.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto getUserById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException("User Not found With Id : " + userId, HttpStatus.NOT_FOUND));
        return userToDto(user);
    }

    @Override
    public List <UserDto> getAllUsers() {
        List <User> users = userRepository.findAll();
        List <UserDto> userDtos = users.stream()
                .map(user -> this.userToDto(user))
                .toList();
        return userDtos;
    }

    @Override
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException("User Not found With Id : " + userId, HttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }

    public User dtoToUser(UserDto userDto){
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());

        User user = modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user){
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

}
