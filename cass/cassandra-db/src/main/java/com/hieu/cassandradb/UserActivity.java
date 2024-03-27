package com.hieu.cassandradb;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

@Table(value = "user_activity")
@Data
@AllArgsConstructor
public class UserActivity {
    private String userId;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED,ordinal = 0,value = "activity_type")
    private String activityType;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED,ordinal = 1,value = "timestamp")
    private Instant timestamp;
    private String details;
}
