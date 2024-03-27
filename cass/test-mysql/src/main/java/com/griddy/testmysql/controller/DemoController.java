package com.griddy.testmysql.controller;

import com.griddy.testmysql.model.UserActivity;
import com.griddy.testmysql.repository.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class DemoController {
    @Autowired
    private UserActivityRepository userActivityRepository;

    @GetMapping("/")
    public List<UserActivity> getAll() {
        return userActivityRepository.findAll();
    }

    @GetMapping("/{type}")
    private List<UserActivity> getByType(@PathVariable String type) {
        return userActivityRepository.findByActivityType(type);
    }
}
