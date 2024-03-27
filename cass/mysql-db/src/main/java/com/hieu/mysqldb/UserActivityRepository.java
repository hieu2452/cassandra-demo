package com.hieu.mysqldb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity,String> {
}
