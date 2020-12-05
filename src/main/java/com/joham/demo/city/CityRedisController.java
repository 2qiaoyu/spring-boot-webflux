package com.joham.demo.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author joham
 */
@RestController
@RequestMapping(value = "/city/redis")
public class CityRedisController {

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    @GetMapping(value = "/{id}")
    public Mono<CityRedis> findCityById(@PathVariable("id") Long id) {
        String key = "city_" + id;
        ReactiveValueOperations<String, CityRedis> operations = reactiveRedisTemplate.opsForValue();
        Mono<CityRedis> city = operations.get(key);
        return city;
    }

    @PostMapping
    public Mono<CityRedis> saveCity(@RequestBody CityRedis city) {
        String key = "city_" + city.getId();
        ReactiveValueOperations<String, CityRedis> operations = reactiveRedisTemplate.opsForValue();
        return operations.getAndSet(key, city);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        String key = "city_" + id;
        return reactiveRedisTemplate.delete(key);
    }
}
