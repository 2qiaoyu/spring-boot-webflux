package com.joham.demo.city;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author joham
 */
@Component
@Slf4j
public class CityHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CityRedisRepository cityRedisRepository;

    public Mono<ServerResponse> helloCity(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello, City!"));
    }

    public Mono<CityRedis> save(CityRedis city) {
        return cityRedisRepository.save(city);
    }


    public Mono<CityRedis> findCityById(Long id) {

        // 从缓存中获取城市信息
        String key = "city_" + id;
        ValueOperations<String, CityRedis> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            CityRedis city = operations.get(key);

            log.info("CityHandler.findCityById() : 从缓存中获取了城市 >> " + city.toString());
            return Mono.create(cityMonoSink -> cityMonoSink.success(city));
        }

        // 从 MongoDB 中获取城市信息
        Mono<CityRedis> cityMono = cityRedisRepository.findById(id);

        if (cityMono == null) {
            return null;
        }

        // 插入缓存
        cityMono.subscribe(cityObj -> {
            operations.set(key, cityObj);
            log.info("CityHandler.findCityById() : 城市插入缓存 >> " + cityObj.toString());
        });

        return cityMono;
    }

    public Flux<CityRedis> findAllCity() {
        return cityRedisRepository.findAll().cache();
    }

    public Mono<CityRedis> modifyCity(CityRedis city) {

        Mono<CityRedis> cityMono = cityRedisRepository.save(city);

        // 缓存存在，删除缓存
        String key = "city_" + city.getId();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);

            log.info("CityHandler.modifyCity() : 从缓存中删除城市 ID >> " + city.getId());
        }

        return cityMono;
    }

    public Mono<Long> deleteCity(Long id) {

        cityRedisRepository.deleteById(id);

        // 缓存存在，删除缓存
        String key = "city_" + id;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);

            log.info("CityHandler.deleteCity() : 从缓存中删除城市 ID >> " + id);
        }

        return Mono.create(cityMonoSink -> cityMonoSink.success(id));
    }
}
