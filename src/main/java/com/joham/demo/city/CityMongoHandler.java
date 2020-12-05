package com.joham.demo.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author joham
 */
@Component
public class CityMongoHandler {

    @Autowired
    private CityMongoRepository cityMongoRepository;

    public Mono<City> save(City city) {
        return cityMongoRepository.save(city);
    }

    public Mono<City> findCityById(Long id) {

        return cityMongoRepository.findById(id);
    }

    public Flux<City> findAllCity() {

        return cityMongoRepository.findAll();
    }

    public Mono<City> modifyCity(City city) {

        return cityMongoRepository.save(city);
    }

    public Mono<Long> deleteCity(Long id) {
        cityMongoRepository.deleteById(id);
        return Mono.create(cityMonoSink -> cityMonoSink.success(id));
    }

    public Mono<City> getByCityName(String cityName) {
        return cityMongoRepository.findByCityName(cityName);
    }
}
