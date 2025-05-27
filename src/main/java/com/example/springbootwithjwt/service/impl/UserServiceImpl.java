package com.example.springbootwithjwt.service.impl;

import com.example.springbootwithjwt.dto.UserDto;
import com.example.springbootwithjwt.entity.User;
import com.example.springbootwithjwt.repository.UserRepository;
import com.example.springbootwithjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user=new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());
        user.setProvider(userDto.getProvider());
        User savedUser=userRepository.save(user);
        return new UserDto(savedUser.getId(),savedUser.getName(),savedUser.getEmail(),savedUser.getPhone(),savedUser.getPassword(),savedUser.getProvider());

    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User update=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        update.setName(userDto.getName());
        update.setEmail(userDto.getEmail());
        update.setPhone(userDto.getPhone());
        update.setPassword(userDto.getPassword());
        update.setProvider(userDto.getProvider());
        User user=userRepository.save(update);
        return new UserDto(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getPassword(),user.getProvider());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(user->new UserDto(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getPassword(),user.getProvider())).orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user->new UserDto(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getPassword(),user.getProvider())).toList();
    }
}
