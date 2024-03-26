package com.hieu.cassandra.service;

import com.hieu.cassandra.dto.UserDto;
import com.hieu.cassandra.model.UserById;
import com.hieu.cassandra.repository.UserByIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    public UserByIdRepository userByIdRepository;

    public UserDto saveUser(UserDto userDto) {
        UserById newUser = new UserById(userDto.getCountry(), userDto.getCity(), userDto.getAge(),userDto.getName());
        UserById savedUser = userByIdRepository.save(newUser);

        return new UserDto(savedUser.getCountry(),savedUser.getCity(), savedUser.getAge(), savedUser.getId(), savedUser.getName());
    }

    public List<UserDto> findAllUsers() {
        return userByIdRepository.findAll().stream().map(user ->
            new UserDto(user.getCountry(),user.getCity(), user.getAge(), user.getId(),user.getName())
        ).collect(Collectors.toList());
    }

    public UserDto findUserById(long id) {
        UserById user = userByIdRepository.findById(id).orElseThrow();
        return new UserDto(user.getCountry(),user.getCity(), user.getAge(), user.getId(), user.getName());
    }
}
