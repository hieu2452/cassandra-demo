package com.hieu.price.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(value = "transaction_by_user_id")
public class TransactionByUserId {

    private UUID id;
    @PrimaryKeyColumn(name = "purchase_time", ordinal = 1,type = PrimaryKeyType.CLUSTERED)
    private LocalDateTime purchaseTime;
    private double purchasePrice;

    @PrimaryKeyColumn(name = "userId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userId;

}
