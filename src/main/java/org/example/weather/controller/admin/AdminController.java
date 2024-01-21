package org.example.weather.controller.admin;


import lombok.RequiredArgsConstructor;
import org.example.weather.controller.BaseController;
import org.example.weather.domain.entity.City;
import org.example.weather.domain.entity.Subscription;
import org.example.weather.domain.entity.User;
import org.example.weather.domain.entity.WeatherData;
import org.example.weather.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController extends BaseController {

    private final AdminService adminService;

    @GetMapping("/user-list")
    public Flux<User> getUserList() {
        return adminService.getUserList();
    }


    @GetMapping("/user-details/{userId}")
    public Mono<ResponseEntity<Subscription>> getUserDetails(@PathVariable Long userId) {
        return adminService.getUserDetails(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PutMapping("/edit-user/{userId}")
    public Mono<ResponseEntity<User>> editUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        return adminService.editUser(userId, updatedUser)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @GetMapping("/cities-list")
    public Flux<City> getCitiesList() {
        return adminService.getCitiesList();
    }


    @PutMapping("/edit-city/{cityId}")
    public Mono<ResponseEntity<City>> editCity(@PathVariable Long cityId, @RequestBody City updatedCity) {
        return adminService.editCity(cityId, updatedCity)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PutMapping("/update-city-weather/{cityId}")
    public Mono<ResponseEntity<WeatherData>> updateCityWeather(@PathVariable Long cityId, @RequestBody WeatherData updatedWeatherData) {
        return adminService.updateCityWeather(cityId, updatedWeatherData)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
