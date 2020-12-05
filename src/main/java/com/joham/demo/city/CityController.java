package com.joham.demo.city;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author joham
 */
@Controller
@Slf4j
@RequestMapping(value = "/city")
public class CityController {

    private static final String CITY_FORM_PATH_NAME = "cityForm";
    private static final String CITY_LIST_PATH_NAME = "cityList";
    private static final String REDIRECT_TO_CITY_URL = "redirect:/city";

    @Autowired
    CityService cityService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCityList(final Model model) {
        model.addAttribute("cityList", cityService.findAll());
        return CITY_LIST_PATH_NAME;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createCityForm(final Model model) {
        model.addAttribute("city", new City());
        model.addAttribute("action", "create");
        return CITY_FORM_PATH_NAME;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Mono<String> postCity(@ModelAttribute City city) {
        return cityService.insertByCity(city).thenReturn(REDIRECT_TO_CITY_URL);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String getCity(@PathVariable Long id, final Model model) {
        final Mono<City> city = cityService.findById(id);
        model.addAttribute("city", city);
        model.addAttribute("action", "update");
        return CITY_FORM_PATH_NAME;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Mono<String> putBook(@ModelAttribute City city) {
        return cityService.update(city).thenReturn(REDIRECT_TO_CITY_URL);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Mono<String> deleteCity(@PathVariable Long id) {
        return cityService.delete(id).thenReturn(REDIRECT_TO_CITY_URL);
    }
}
