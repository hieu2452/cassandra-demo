package com.hieu.useractivity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserActivity {
    private String userId;
    private String activityType;
    private Instant timestamp;
    private String details;
}
