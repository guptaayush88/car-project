package com.unfi.codechallenges.cars.controller;

import com.unfi.codechallenges.cars.dto.CarDto;
import com.unfi.codechallenges.cars.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cars")
public class CarsController {

    private final CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        log.info("Getting all active cars");
        return ResponseEntity.ok(carService.getAll());
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto car) {
        log.info("creating car");
        return ResponseEntity.ok(carService.createCar(car));
    }

    @PutMapping (path = "/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id ,@RequestBody CarDto car) {
        log.info("Updating car");
        return ResponseEntity.ok(carService.update(id, car));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CarDto> deleteCar(@PathVariable Long id) {
        log.info("Deleting car");
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}