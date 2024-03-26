package com.hieu.cassandra.repository;

import com.hieu.cassandra.model.BitcoinPrice;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BitcoinPriceRepository extends CassandraRepository<BitcoinPrice, UUID> {

   List<BitcoinPrice> findByIdIn(List<UUID> ids);
}
