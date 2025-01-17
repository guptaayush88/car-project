package com.unfi.codechallenges.cars.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.unfi.codechallenges.cars.ExceptionHandler.CarNotFoundException;
import com.unfi.codechallenges.cars.dto.CarDto;
import com.unfi.codechallenges.cars.entity.Car;
import com.unfi.codechallenges.cars.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void testProcessUser() {
        CarDto car = CarDto.builder().make("Audi").model("S4").year("2020").vin("ASEDF908U9F983HA").build();

        // Expecting an exception due to the deliberate bug setting name to null
        when(carRepository.save(any(Car.class))).thenThrow(new IllegalArgumentException("Name cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> carService.createCar(car));
    }

    // Negative Test Case for updateCar
    @Test
    public void testUpdateCar() {
        CarDto car = CarDto.builder().make("Audi").model("S4").year("2020").vin("ASEDF908U9F983HA").build();
        Optional<Car> optionalCar = Optional.empty();
        when(carRepository.findById(12L)).thenReturn(optionalCar);
        when(carRepository.save(any(Car.class))).thenThrow(new CarNotFoundException("Car not found"));
        assertThrows(CarNotFoundException.class, () -> carService.update(12L, car));
    }

    // Positive Test Case for updateCar
    @Test
    public void existUpdateCar() {
        CarDto car = CarDto.builder().id(1L).make("Audi").model("S4").year("2020").vin("ASEDF908U9F983HA").build();
        CarDto updateCar = CarDto.builder().id(12L).make("Kia").model("A4").year("2023").vin("DBKDF908U9F983AH").build();
        Car carEntity = new Car();
        carEntity.setId(car.getId());
        carEntity.setMake(updateCar.getMake());
        carEntity.setModel(updateCar.getModel());
        carEntity.setYear(updateCar.getYear());
        carEntity.setVin(updateCar.getVin());

        when(carRepository.findById(1L)).thenReturn(Optional.of(carEntity));
        when(carRepository.save(carEntity)).thenReturn(carEntity);

        carService.update(1L, car);

    }

    // Negative Test Case for deleteCar
    @Test
    public void deleteCar() {
        CarDto car = CarDto.builder().make("Audi").model("S4").year("2020").vin("ASEDF908U9F983HA").build();
        when(carRepository.findById(12L)).thenReturn(Optional.empty());
        when(carRepository.save(any(Car.class))).thenThrow(new CarNotFoundException("Car not found"));
        assertThrows(CarNotFoundException.class, () -> carService.delete(12L));
    }

    // Positive Test Case for deleteCar
    @Test
    public void existDeleteCar() {
        Car carEntity = new Car();
        carEntity.setId(carEntity.getId());
        carEntity.setIsActive(true);

        when(carRepository.findById(12L)).thenReturn(Optional.of(carEntity));
        carService.delete(12L);
    }

    // Negative Test Case for getCars
    @Test
    public void test_getCars() {
        CarDto car = CarDto.builder().make("Audi").model("S4").year("2020").vin("ASEDF908U9F983HA").build();
        Optional<Car> optionalCar = Optional.empty();

        when(carRepository.findAllByIsActiveTrue()).thenThrow(new CarNotFoundException("Car not found"));
        assertThrows(CarNotFoundException.class , () -> carService.getAll());

    }

    // Positive Test Case for getCars
    @Test
    public void test_getAllCars() {
        CarDto car = CarDto.builder().make("Audi").model("S4").year("2020").vin("ASEDF908U9F983HA").build();
        Car carEntity = new Car();
        car.setId(car.getId());
        car.setMake(car.getMake());
        car.setModel(car.getModel());
        car.setYear(car.getYear());
        car.setVin(car.getVin());

        when(carRepository.findAllByIsActiveTrue()).thenReturn(List.of(carEntity));
        when(carRepository.save(carEntity)).thenReturn(carEntity);

        carService.getAll();

    }


}