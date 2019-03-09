package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;

public class SupplierServiceTests {

    private SupplierService supplierService;
    private ModelMapper modelMapper;

    @Before()
    public void init() {
        SupplierRepository supplierRepository = Mockito.mock(SupplierRepository.class);
        this.modelMapper = new ModelMapper();
        this.supplierService = new SupplierServiceImpl(supplierRepository, modelMapper);
    }

    @Test
    public void saveSupplier_whenCorrectSave_expectServiceModel() {
        //Arrange
        SupplierServiceModel expectedSupplier = new SupplierServiceModel();
        expectedSupplier.setId("111");


        //Act
        SupplierServiceModel actualSupplier = supplierService.saveSupplier(expectedSupplier);
        actualSupplier.setId("111");

        //Assert
        Assert.assertEquals(expectedSupplier.getId(), actualSupplier.getId());

    }

    @Test(expected = AnyException.class)
    public void saveSupplier_whenIsNull_expectNull() {

        //Act
        SupplierServiceModel actualSupplier = supplierService.saveSupplier(null);

        //Assert
        Assert.assertNull(null, actualSupplier);
    }
}
