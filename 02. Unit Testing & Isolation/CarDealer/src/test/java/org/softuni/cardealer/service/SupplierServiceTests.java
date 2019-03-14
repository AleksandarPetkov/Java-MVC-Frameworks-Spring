package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTests {
    private static final String INVALID_ID = "RandomID";

    @Autowired
    private SupplierRepository supplierRepository;

    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void saveSupplier_whenCorrectSave_expectCorrectSave() {
        //Arrange
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);
        SupplierServiceModel supplierTest = new SupplierServiceModel();
        supplierTest.setName("XXX");
        supplierTest.setImporter(true);

        //Act
        SupplierServiceModel actualSupplier = supplierService.saveSupplier(supplierTest);
        SupplierServiceModel expectedSupplier = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        //Assert
        Assert.assertEquals(expectedSupplier.getId(), actualSupplier.getId());
        Assert.assertEquals(expectedSupplier.getName(), actualSupplier.getName());
        Assert.assertEquals(expectedSupplier.isImporter(), actualSupplier.isImporter());
    }

    @Test(expected = Exception.class)
    public void saveSupplier_whenSaveSupplier_expectException() {
        //Arrange
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);
        SupplierServiceModel supplierTest = new SupplierServiceModel();
        supplierTest.setName(null);
        supplierTest.setImporter(true);

        //Act and Assert Exception
        SupplierServiceModel actualSupplier = supplierService.saveSupplier(supplierTest);

    }

    @Test
    public void editSupplier_whenCorrectEdit_expectCorrectEdit() {
        //Arrange
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);
        
        Supplier supplierTest = new Supplier();
        supplierTest.setName("Dori");
        supplierTest.setImporter(true);
        this.supplierRepository.save(supplierTest);

        SupplierServiceModel methodSupplier = new SupplierServiceModel();
        methodSupplier.setId(supplierTest.getId());
        methodSupplier.setName("AAA");
        methodSupplier.setImporter(false);

        //Act
        SupplierServiceModel actualSupplier = supplierService.editSupplier(methodSupplier);
        SupplierServiceModel expectedSupplier = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        //Assert
        Assert.assertEquals(expectedSupplier.getId(), actualSupplier.getId());
        Assert.assertEquals(expectedSupplier.getName(), actualSupplier.getName());
        Assert.assertEquals(expectedSupplier.isImporter(), actualSupplier.isImporter());

    }

    @Test(expected = Exception.class)
    public void editSupplier_whenHaveNull_expectException(){
        //Arrange
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);

        Supplier supplierTest = new Supplier();
        supplierTest.setName("Dori");
        supplierTest.setImporter(true);
        this.supplierRepository.save(supplierTest);

        //Act and Assert Exception
        SupplierServiceModel methodSupplier = new SupplierServiceModel();
        methodSupplier.setId(supplierTest.getId());
        methodSupplier.setName(null);
        methodSupplier.setImporter(false);

        SupplierServiceModel actualSupplier = supplierService.editSupplier(methodSupplier);

    }

    @Test
    public void deleteSupplier_whenDelete_expectCorrectDelete(){
        //Arrange
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);

        Supplier savedSupplier = new Supplier();
        savedSupplier.setName("AAA");
        savedSupplier.setImporter(true);

        this.supplierRepository.save(savedSupplier);

        //Act
        SupplierServiceModel expectedSupplier =  this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        SupplierServiceModel actualSupplier = supplierService.deleteSupplier(savedSupplier.getId());

        //Assert
        Assert.assertEquals(expectedSupplier.getId(), actualSupplier.getId());
        Assert.assertEquals(expectedSupplier.getName(), actualSupplier.getName());
        Assert.assertEquals(expectedSupplier.isImporter(), actualSupplier.isImporter());
    }

    @Test(expected = Exception.class)
    public void deleteSupplier_whenIdIsDifferent_expectException(){
        //Arrange
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);

        Supplier savedSupplier = new Supplier();
        savedSupplier.setName("AAA");
        savedSupplier.setImporter(true);
        this.supplierRepository.save(savedSupplier);

        //Act
        supplierService.deleteSupplier(INVALID_ID);
    }

    @Test
    public void findSupplierById_whenCorrectId_expectCorrectSupplier(){
        //Arrange
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);

        Supplier savedSupplier = new Supplier();
        savedSupplier.setName("AAA");
        savedSupplier.setImporter(true);
        this.supplierRepository.save(savedSupplier);

        //Act
        SupplierServiceModel actualSupplier = supplierService.findSupplierById(savedSupplier.getId());
        SupplierServiceModel expectedSupplier =  this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        //Assert
        Assert.assertEquals(expectedSupplier.getId(), actualSupplier.getId());
        Assert.assertEquals(expectedSupplier.getName(), actualSupplier.getName());
        Assert.assertEquals(expectedSupplier.isImporter(), actualSupplier.isImporter());
    }

    @Test(expected = Exception.class)
    public void findSupplierById_whenIdIsDifferent_expectException(){
        //Arrange
        SupplierService supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);

        Supplier savedSupplier = new Supplier();
        savedSupplier.setName("AAA");
        savedSupplier.setImporter(true);
        this.supplierRepository.save(savedSupplier);

        //Act
        supplierService.findSupplierById(INVALID_ID);
    }

}
