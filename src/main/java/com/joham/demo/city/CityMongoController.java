package com.joham.demo.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author joham
 */
@Controller
@RequestMapping(value = "/city/mongo")
public class CityMongoController {

    @Autowired
    private CityMongoHandler cityMongoHandler;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        return cityMongoHandler.findCityById(id);
    }

    @GetMapping()
    @ResponseBody
    public Flux<City> findAllCity() {
        return cityMongoHandler.findAllCity();
    }

    @PostMapping()
    @ResponseBody
    public Mono<City> saveCity(@RequestBody City city) {
        return cityMongoHandler.save(city);
    }

    @PutMapping()
    @ResponseBody
    public Mono<City> modifyCity(@RequestBody City city) {
        return cityMongoHandler.modifyCity(city);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        return cityMongoHandler.deleteCity(id);
    }

    private static final String CITY_LIST_PATH_NAME = "cityList";
    private static final String CITY_PATH_NAME = "city";

    @GetMapping("/page/list")
    public String listPage(final Model model) {
        final Flux<City> cityFluxList = cityMongoHandler.findAllCity();
        model.addAttribute("cityList", cityFluxList);
        return CITY_LIST_PATH_NAME;
    }

    @GetMapping("/getByName")
    public String getByCityName(final Model model,
                                @RequestParam("cityName") String cityName) {
        final Mono<City> city = cityMongoHandler.getByCityName(cityName);
        model.addAttribute("city", city);
        return CITY_PATH_NAME;
    }
}
