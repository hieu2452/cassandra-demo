package com.hieu.cassandradb;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserActivityRepository extends CassandraRepository<UserActivity,String> {
}
