package com.hamzaKhan.fullStackbackEnd.customer;

import com.hamzaKhan.fullStackbackEnd.AbstractTestcontainers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        //When
        underTest.selectAllCustomers();

        //Then
        Mockito.verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        //Given
        int id = 1;

        //When
        underTest.selectCustomerById(id);

        //Then
        Mockito.verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        //Given
        Customer customer = new Customer(
                1, "Ezio", "ezio@ubisoft.com", 27
        );

        //When
        underTest.insertCustomer(customer);

        //Then
        Mockito.verify(customerRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        //Given
        String email = "deezNuts@assassinsCreed.com";

        //When
        underTest.existsPersonWithEmail(email);

        //Then
        Mockito.verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        //Given
        int id = 4;

        //When
        underTest.existsPersonWithId(id);

        //Then
        Mockito.verify(customerRepository).existsById(id);
    }

    @Test
    void deleteCustomer() {
        //Given
        int id = 44;

        //When
        underTest.deleteCustomer(id);

        //Then
        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        //Given
        Customer customer = new Customer(
                1, "Ezio", "ezio@ubisoft.com", 27
        );

        //When
        underTest.updateCustomer(customer);

        //Then
        Mockito.verify(customerRepository).save(customer);
    }
}