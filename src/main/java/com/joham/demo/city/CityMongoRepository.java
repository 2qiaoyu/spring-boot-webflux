package com.joham.demo.city;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author joham
 */
@Repository
public interface CityMongoRepository extends ReactiveMongoRepository<City, Long> {

    Mono<City> findByCityName(String cityName);
}
