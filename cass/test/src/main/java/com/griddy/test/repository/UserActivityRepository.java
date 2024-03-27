package com.griddy.test.repository;

import com.griddy.test.model.UserActivity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface UserActivityRepository extends CassandraRepository<UserActivity,String> {

    List<UserActivity> findByActivityType(String activityType);
}
