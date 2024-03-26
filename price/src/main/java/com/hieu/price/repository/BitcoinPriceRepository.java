package com.hieu.price.repository;

import com.hieu.price.model.BitcoinPrice;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface BitcoinPriceRepository extends CassandraRepository<BitcoinPrice, UUID> {

   List<BitcoinPrice> findByIdIn(List<UUID> ids);
}
