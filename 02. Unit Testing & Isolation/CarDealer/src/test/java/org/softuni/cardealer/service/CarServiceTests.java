package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTests {

    private static final String INVALID_ID = "RandomID";

    @Autowired
    private CarRepository carRepository;

    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void saveCar_whenSaveIsCorrect_expectSaveCar() {
        //Arrange
        CarService carService = new CarServiceImpl(carRepository, modelMapper);
        CarServiceModel savedCar = new CarServiceModel();
        savedCar.setMake("VW");
        savedCar.setModel("Golf");
        savedCar.setTravelledDistance(10000L);

        //Act
        CarServiceModel actualCar = carService.saveCar(savedCar);
        CarServiceModel expectCar = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);

        //Assert
        Assert.assertEquals(expectCar.getId(), actualCar.getId());
        Assert.assertEquals(expectCar.getMake(), actualCar.getMake());
        Assert.assertEquals(expectCar.getModel(), actualCar.getModel());
        Assert.assertEquals(expectCar.getTravelledDistance(), actualCar.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void saveCar_whenHaveIncorrectCarData_expectException() {
        //Arrange
        CarService carService = new CarServiceImpl(carRepository, modelMapper);
        CarServiceModel savedCar = new CarServiceModel();
        savedCar.setMake(null);
        savedCar.setModel(null);
        savedCar.setTravelledDistance(10000L);

        //Act
        carService.saveCar(savedCar);
    }

    @Test
    public void editCar_whenEditIsCorrect_expectEditCar() {
        //Arrange
        CarService carService = new CarServiceImpl(carRepository, modelMapper);

        Car carInDB = new Car();
        carInDB.setMake("BMW");
        carInDB.setModel("M");
        carInDB.setTravelledDistance(999L);
        this.carRepository.save(carInDB);

        CarServiceModel editCar = new CarServiceModel();
        editCar.setId(carInDB.getId());
        editCar.setMake("VW");
        editCar.setModel("Golf");
        editCar.setTravelledDistance(10000L);

        //Act
        CarServiceModel actualCar = carService.editCar(editCar);
        CarServiceModel expectCar = this.modelMapper.map(carRepository.findAll().get(0), CarServiceModel.class);

        //Assert
        Assert.assertEquals(expectCar.getId(), actualCar.getId());
        Assert.assertEquals(expectCar.getMake(), actualCar.getMake());
        Assert.assertEquals(expectCar.getModel(), actualCar.getModel());
        Assert.assertEquals(expectCar.getTravelledDistance(), actualCar.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void editCar_whenCarIdIsIncorrect_expectException() {
        //Arrange
        CarService carService = new CarServiceImpl(carRepository, modelMapper);

        Car carInDB = new Car();
        carInDB.setMake("BMW");
        carInDB.setModel("M");
        carInDB.setTravelledDistance(999L);
        this.carRepository.save(carInDB);

        CarServiceModel editCar = new CarServiceModel();
        editCar.setId(INVALID_ID);
        editCar.setMake("VW");
        editCar.setModel("Golf");
        editCar.setTravelledDistance(10000L);

        //Act
        carService.editCar(editCar);
    }

    @Test
    public void deleteCar_whenDeleteCar_expectCorrectDelete() {
        //Arrange
        CarService carService = new CarServiceImpl(carRepository, modelMapper);

        Car carInDB = new Car();
        carInDB.setMake("BMW");
        carInDB.setModel("M");
        carInDB.setTravelledDistance(999L);
        this.carRepository.save(carInDB);

        //Act
        CarServiceModel expectCar = this.modelMapper.map(carRepository.findAll().get(0), CarServiceModel.class);
        CarServiceModel actualCar = carService.deleteCar(carInDB.getId());
        long expectedCarCount = 0;
        long actualCarCount = this.carRepository.count();

        //Assert
        Assert.assertEquals(expectCar.getId(), actualCar.getId());
        Assert.assertEquals(expectCar.getMake(), actualCar.getMake());
        Assert.assertEquals(expectCar.getModel(), actualCar.getModel());
        Assert.assertEquals(expectCar.getTravelledDistance(), actualCar.getTravelledDistance());
        Assert.assertEquals(expectedCarCount, actualCarCount);
    }

    @Test(expected = Exception.class)
    public void deleteCar_whenDCarIdIsNotCorrect_expectException() {
        //Arrange
        CarService carService = new CarServiceImpl(carRepository, modelMapper);

        Car carInDB = new Car();
        carInDB.setMake("BMW");
        carInDB.setModel("M");
        carInDB.setTravelledDistance(999L);
        this.carRepository.save(carInDB);

        //Act
        carService.deleteCar(INVALID_ID);
    }

    @Test
    public void findCarById_whenIdIsCorrect_expectFindCarById(){
        //Arrange
        CarService carService = new CarServiceImpl(carRepository, modelMapper);

        Car carInDB = new Car();
        carInDB.setMake("BMW");
        carInDB.setModel("M");
        carInDB.setTravelledDistance(999L);
        this.carRepository.save(carInDB);

        //Act
        CarServiceModel actualCar = carService.findCarById(carInDB.getId());
        CarServiceModel expectCar = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);

        //Assert
        Assert.assertEquals(expectCar.getId(), actualCar.getId());
        Assert.assertEquals(expectCar.getMake(), actualCar.getMake());
        Assert.assertEquals(expectCar.getModel(), actualCar.getModel());
        Assert.assertEquals(expectCar.getTravelledDistance(), actualCar.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void findCarById_whenIdIsNotCorrect_expectException(){
        //Arrange
        CarService carService = new CarServiceImpl(carRepository, modelMapper);

        Car carInDB = new Car();
        carInDB.setMake("BMW");
        carInDB.setModel("M");
        carInDB.setTravelledDistance(999L);
        this.carRepository.save(carInDB);

        //Act
        CarServiceModel actualCar = carService.findCarById(INVALID_ID);
    }
}
