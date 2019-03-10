package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTests {
    private static final String INVALID_ID = "RandomID";

    @Autowired
    private PartRepository partRepository;

    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void savePart_whenCorrectSavePart_expectSavePart() {
        //Arrange
        PartService partService = new PartServiceImpl(partRepository, modelMapper);

        PartServiceModel part = new PartServiceModel();
        part.setName("Gearbox");
        part.setPrice(BigDecimal.TEN);

        //Act
        PartServiceModel actualPartModel = partService.savePart(part);
        PartServiceModel expectedPartModel = this.modelMapper
                .map(this.partRepository.findAll().get(0), PartServiceModel.class);

        //Assert
        Assert.assertEquals(expectedPartModel.getId(), actualPartModel.getId());
        Assert.assertEquals(expectedPartModel.getName(), actualPartModel.getName());
        Assert.assertEquals(expectedPartModel.getPrice(), actualPartModel.getPrice());
    }

    @Test(expected = Exception.class)
    public void savePart_whenSaveIncorrectPart_expectException() {
        //Arrange
        PartService partService = new PartServiceImpl(partRepository, modelMapper);

        PartServiceModel incorrectPart = new PartServiceModel();
        incorrectPart.setName(null);
        incorrectPart.setPrice(BigDecimal.TEN);

        //Act and Assert Exception
        partService.savePart(incorrectPart);
    }

    @Test
    public void editPart_whenCorrectEdit_expectCorrectEditPart() {
        //Arrange
        PartService partService = new PartServiceImpl(partRepository, modelMapper);

        Part partInDB = new Part();
        partInDB.setName("Gearbox");
        partInDB.setPrice(BigDecimal.TEN);
        this.partRepository.save(partInDB);

        PartServiceModel partForEdit = new PartServiceModel();
        partForEdit.setId(partInDB.getId());
        partForEdit.setName("Engine");
        partForEdit.setPrice(BigDecimal.ONE);

        //Act
        PartServiceModel actualPartModel = partService.editPart(partForEdit);
        PartServiceModel expectedPartModel = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);

        //Assert
        Assert.assertEquals(expectedPartModel.getId(), actualPartModel.getId());
        Assert.assertEquals(expectedPartModel.getName(), actualPartModel.getName());
        Assert.assertEquals(expectedPartModel.getPrice(), actualPartModel.getPrice());
    }

    @Test(expected = Exception.class)
    public void editPart_whenHaveNull_expectException() {
        //Arrange
        PartService partService = new PartServiceImpl(partRepository, modelMapper);

        Part partInDB = new Part();
        partInDB.setName("Gearbox");
        partInDB.setPrice(BigDecimal.TEN);
        this.partRepository.save(partInDB);

        PartServiceModel partForEdit = new PartServiceModel();
        partForEdit.setId(partInDB.getId());
        partForEdit.setName(null);
        partForEdit.setPrice(null);

        //Act
        partService.editPart(partForEdit);
    }

    @Test
    public void deletePart_whenDeletePart_expectCorrectDelete() {
        //Arrange
        PartService partService = new PartServiceImpl(partRepository, modelMapper);

        Part partInDB = new Part();
        partInDB.setName("Gearbox");
        partInDB.setPrice(BigDecimal.TEN);
        this.partRepository.save(partInDB);

        //Act
        PartServiceModel expectedPartModel = this.modelMapper.map(partRepository.findAll().get(0), PartServiceModel.class);
        PartServiceModel actualPartModel = partService.deletePart(partInDB.getId());
        long expectedPartCount = 0;
        long actualPartCount = this.partRepository.count();

        //Assert
        Assert.assertEquals(expectedPartModel.getId(), actualPartModel.getId());
        Assert.assertEquals(expectedPartModel.getName(), actualPartModel.getName());
        Assert.assertEquals(expectedPartModel.getPrice(), actualPartModel.getPrice());
        Assert.assertEquals(expectedPartCount, actualPartCount);
    }

    @Test(expected = Exception.class)
    public void deletePart_whenHaveIncorrectID_expectException() {
        //Arrange
        PartService partService = new PartServiceImpl(partRepository, modelMapper);

        Part partInDB = new Part();
        partInDB.setName("Gearbox");
        partInDB.setPrice(BigDecimal.TEN);
        this.partRepository.save(partInDB);

        //Act
        partService.deletePart(INVALID_ID);
    }

    @Test
    public void findPartById_whenHaveCorrectId_expectCorrectPart(){
        //Arrange
        PartService partService = new PartServiceImpl(partRepository, modelMapper);

        Part partInDB = new Part();
        partInDB.setName("Gearbox");
        partInDB.setPrice(BigDecimal.TEN);
        this.partRepository.save(partInDB);

        //Act
        PartServiceModel actualPartModel = partService.findPartById(partInDB.getId());
        PartServiceModel expectedPartModel = this.modelMapper.map(partRepository.findAll().get(0), PartServiceModel.class);

        //Assert
        Assert.assertEquals(expectedPartModel.getId(), actualPartModel.getId());
        Assert.assertEquals(expectedPartModel.getName(), actualPartModel.getName());
        Assert.assertEquals(expectedPartModel.getPrice(), actualPartModel.getPrice());
    }

    @Test(expected = Exception.class)
    public void findPartById_whenHaveIncorrectId_expectException() {
        //Arrange
        PartService partService = new PartServiceImpl(partRepository, modelMapper);

        Part partInDB = new Part();
        partInDB.setName("Gearbox");
        partInDB.setPrice(BigDecimal.TEN);
        this.partRepository.save(partInDB);

        //Act
        partService.findPartById(INVALID_ID);
    }
}
