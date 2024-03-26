package com.hieu.cassandra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDto {
    private String country;
    private String city;
    private int age;
    private UUID id;
    private String name;
}
