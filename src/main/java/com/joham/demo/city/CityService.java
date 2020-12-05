package com.joham.demo.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author joham
 */
@Component
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public Flux<City> findAll() {
        return cityRepository.findAll();
    }

    public Mono<City> insertByCity(City city) {
        return cityRepository.save(city);
    }

    public Mono<City> update(City city) {
        return cityRepository.save(city);
    }

    public Mono<Void> delete(Long id) {
        return cityRepository.deleteById(id);
    }

    public Mono<City> findById(Long id) {
        return cityRepository.findById(id);
    }
}
