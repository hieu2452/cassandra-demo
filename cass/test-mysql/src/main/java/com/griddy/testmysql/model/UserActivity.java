package com.griddy.testmysql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
public class UserActivity {
    @Id
    private String userId;
    private String activityType;
    private Instant timestamp;
    private String details;
    public UserActivity() {

    }
}
