package com.hieu.cassandra.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table(value = "user")
@Data
@NoArgsConstructor
public class UserById {
    private String country;

    private String city;
    private String name;

    @PrimaryKeyColumn(name = "age", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    private int age;

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    public UserById(String country, String city, int age,String name) {
        this.country = country;
        this.city = city;
        this.age = age;
        this.name = name;
        this.id = Uuids.timeBased();
    }
}
