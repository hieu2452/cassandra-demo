package com.hieu.cassandra.repository;

import com.hieu.cassandra.model.UserById;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserByIdRepository extends CassandraRepository<UserById,Long> {

}
