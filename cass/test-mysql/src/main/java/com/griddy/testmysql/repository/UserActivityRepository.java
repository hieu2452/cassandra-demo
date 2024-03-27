package com.griddy.testmysql.repository;

import com.griddy.testmysql.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActivityRepository extends JpaRepository<UserActivity,String> {
    List<UserActivity> findByActivityType(String type);
}
