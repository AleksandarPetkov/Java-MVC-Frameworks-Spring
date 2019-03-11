package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceTests {

    private static final String INVALID_ID = "RandomID";

    @Autowired
    private CustomerRepository customerRepository;

    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void saveCustomer_whenCorrectSave_expectSaveCustomer() {
        //Arrange
        CustomerService customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        CustomerServiceModel customerInDB = new CustomerServiceModel();
        customerInDB.setName("PENO");
        customerInDB.setBirthDate(LocalDate.now());
        customerInDB.setYoungDriver(true);

        //Act
        CustomerServiceModel actualCustomer = customerService.saveCustomer(customerInDB);
        CustomerServiceModel expectedCustomer = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);

        //Assert
        Assert.assertEquals(expectedCustomer.getId(), actualCustomer.getId());
        Assert.assertEquals(expectedCustomer.getName(), actualCustomer.getName());
        Assert.assertEquals(expectedCustomer.getBirthDate(), actualCustomer.getBirthDate());
        Assert.assertEquals(expectedCustomer.isYoungDriver(), actualCustomer.isYoungDriver());

    }

    @Test(expected = Exception.class)
    public void saveCustomer_whenHaveInvalidValue_expectException() {
        //Arrange
        CustomerService customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        CustomerServiceModel customerInDB = new CustomerServiceModel();
        customerInDB.setName(null);
        customerInDB.setBirthDate(null);
        customerInDB.setYoungDriver(true);

        //Act
        customerService.saveCustomer(customerInDB);
    }

    @Test
    public void editCustomer_whenCorrectEdit_expectEditCustomer(){
        //Arrange
        CustomerService customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        Customer customerInDB = new Customer();
        customerInDB.setName("PENO");
        customerInDB.setBirthDate(LocalDate.now());
        customerInDB.setYoungDriver(true);
        this.customerRepository.save(customerInDB);

        CustomerServiceModel customerForEdit= new CustomerServiceModel();
        customerForEdit.setId(customerInDB.getId());
        customerForEdit.setName("JORO");
        customerForEdit.setBirthDate(LocalDate.now());
        customerForEdit.setYoungDriver(false);

        //Act
        CustomerServiceModel actualCustomer = customerService.editCustomer(customerForEdit);
        CustomerServiceModel expectedCustomer = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);

        //Assert
        Assert.assertEquals(expectedCustomer.getId(), actualCustomer.getId());
        Assert.assertEquals(expectedCustomer.getName(), actualCustomer.getName());
        Assert.assertEquals(expectedCustomer.getBirthDate(), actualCustomer.getBirthDate());
        Assert.assertEquals(expectedCustomer.isYoungDriver(), actualCustomer.isYoungDriver());
    }

    @Test(expected = Exception.class)
    public void editCustomer_whenHaveInvalidID_expectException() {
        //Arrange
        CustomerService customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        Customer customerInDB = new Customer();
        customerInDB.setName("PENO");
        customerInDB.setBirthDate(LocalDate.now());
        customerInDB.setYoungDriver(true);
        this.customerRepository.save(customerInDB);

        CustomerServiceModel customerForEdit = new CustomerServiceModel();
        customerForEdit.setId(INVALID_ID);
        customerForEdit.setName("JORO");
        customerForEdit.setBirthDate(LocalDate.now());
        customerForEdit.setYoungDriver(false);

        //Act
        customerService.editCustomer(customerForEdit);
    }

    @Test
    public void deleteCustomer_whenCorrectDelete_expectDeleteCustomer(){
        //Arrange
        CustomerService customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        Customer customerInDB = new Customer();
        customerInDB.setName("PENO");
        customerInDB.setBirthDate(LocalDate.now());
        customerInDB.setYoungDriver(true);
        this.customerRepository.save(customerInDB);

        //Act
        CustomerServiceModel expectedCustomer = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        CustomerServiceModel actualCustomer = customerService.deleteCustomer(customerInDB.getId());
        long expectedCustomerCount = 0;
        long actualCustomerCount = this.customerRepository.count();

        //Assert
        Assert.assertEquals(expectedCustomer.getId(), actualCustomer.getId());
        Assert.assertEquals(expectedCustomer.getName(), actualCustomer.getName());
        Assert.assertEquals(expectedCustomer.getBirthDate(), actualCustomer.getBirthDate());
        Assert.assertEquals(expectedCustomer.isYoungDriver(), actualCustomer.isYoungDriver());
        Assert.assertEquals(expectedCustomerCount, actualCustomerCount);
    }

    @Test(expected = Exception.class)
    public void deleteCustomer_whenHaveInvalidId_expectException() {
        //Arrange
        CustomerService customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        Customer customerInDB = new Customer();
        customerInDB.setName("PENO");
        customerInDB.setBirthDate(LocalDate.now());
        customerInDB.setYoungDriver(true);
        this.customerRepository.save(customerInDB);

        //Act
        CustomerServiceModel actualCustomer = customerService.deleteCustomer(INVALID_ID);
    }

    @Test
    public void findCustomerById_whenHaveCorrectId_expectFindCustomerById(){
        //Arrange
        CustomerService customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        Customer customerInDB = new Customer();
        customerInDB.setName("PENO");
        customerInDB.setBirthDate(LocalDate.now());
        customerInDB.setYoungDriver(true);
        this.customerRepository.save(customerInDB);

        //Act
        CustomerServiceModel actualCustomer = customerService.findCustomerById(customerInDB.getId());
        CustomerServiceModel expectedCustomer = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);

        //Assert
        Assert.assertEquals(expectedCustomer.getId(), actualCustomer.getId());
        Assert.assertEquals(expectedCustomer.getName(), actualCustomer.getName());
        Assert.assertEquals(expectedCustomer.getBirthDate(), actualCustomer.getBirthDate());
        Assert.assertEquals(expectedCustomer.isYoungDriver(), actualCustomer.isYoungDriver());
    }

    @Test(expected = Exception.class)
    public void findCustomerById_whenHaveInvalidId_expectException() {
        //Arrange
        CustomerService customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        Customer customerInDB = new Customer();
        customerInDB.setName("PENO");
        customerInDB.setBirthDate(LocalDate.now());
        customerInDB.setYoungDriver(true);
        this.customerRepository.save(customerInDB);

        //Act
        customerService.findCustomerById(INVALID_ID);
    }
}

