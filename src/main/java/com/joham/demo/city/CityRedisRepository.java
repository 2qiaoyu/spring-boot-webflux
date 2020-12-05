package com.joham.demo.city;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author joham
 */
@Repository
public interface CityRedisRepository extends ReactiveMongoRepository<CityRedis, Long> {

}
