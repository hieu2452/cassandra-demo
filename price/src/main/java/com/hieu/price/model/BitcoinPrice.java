package com.hieu.price.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(value = "bitcoin_prices")
@Data
@AllArgsConstructor
public class BitcoinPrice {
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID id;
    private LocalDateTime timestamp;
    @PrimaryKeyColumn(name = "price", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private double price;
    public BitcoinPrice() {

    }
    public BitcoinPrice(LocalDateTime dateTime,double price) {
        this.timestamp = dateTime;
        this.price = price;
        this.id = Uuids.timeBased();
    }
}
