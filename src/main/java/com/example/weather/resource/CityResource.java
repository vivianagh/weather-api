package com.example.weather.resource;

import com.example.weather.model.io.City;
import com.example.weather.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api")
public class CityResource {
    @Autowired
    private CityService cityService;

    @GetMapping("/city")
    public ResponseEntity<City> searchCity(@RequestParam(required = true) String text) {
        return cityService.searchCity(text).map(ResponseEntity::ok).orElse((ResponseEntity.badRequest().build()));
    }
}
