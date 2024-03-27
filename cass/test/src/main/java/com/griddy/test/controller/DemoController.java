package com.griddy.test.controller;

import com.griddy.test.model.UserActivity;
import com.griddy.test.repository.UserActivityRepository;
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
    public List<UserActivity> getUserActivities() {
        return userActivityRepository.findAll();
    }

    @GetMapping("/{type}")
    public List<UserActivity> getUserActivitiesType(@PathVariable String type) {
        return userActivityRepository.findByActivityType(type);
    }
}
