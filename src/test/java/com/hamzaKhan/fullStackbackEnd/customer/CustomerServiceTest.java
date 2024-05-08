package com.hamzaKhan.fullStackbackEnd.customer;

import com.hamzaKhan.fullStackbackEnd.exception.DuplicateResourceException;
import com.hamzaKhan.fullStackbackEnd.exception.RequestValidationException;
import com.hamzaKhan.fullStackbackEnd.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerDao customerDao;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        //When
        underTest.getAllCustomers();

        //Then
        Mockito.verify(customerDao).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {
        //Given
        int id = 10;
        Customer customer = new Customer(
                id, "David Copperfield", "bizSlim@gmail.com", 31
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //When
        Customer actual = underTest.getCustomer(id);

        //Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowWhenGetCustomerReturnsEmptyOptional() {
        //Given
        int id = 10;
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //When

        //Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "customer with id [%s] not found".formatted(id)
                );
    }

    @Test
    void addCustomer() {
        //Given
        String email = "ezio@ubisoft.com";
        Mockito.when(customerDao.existsPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Ezio Auditore",
                email,
                25
        );

        //When
        underTest.addCustomer(request);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );

        Mockito.verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());

    }

    @Test
    void addCustomerWillThrowWhenEmailExists() {
        //Given
        String email = "ezio@ubisoft.com";
        Mockito.when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Ezio Auditore",
                email,
                25
        );

        //When
//        underTest.addCustomer(request);
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        //Then
        //------ since we never enter the customer adding logic after branching to exception,
        //------ we don't need to capture instance of Customer
//        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
//                Customer.class
//        );

        Mockito.verify(customerDao, Mockito.never()).insertCustomer(Mockito.any());
        //the line above is trying to verify that the insertCustomer method held in the DAO was never executed


        //------ read comment explanation above
        /*Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());*/

    }

    @Test
    void deleteCustomer() {
        //Given
        int id = 7;
        Customer testCustomer = new Customer(
                id, "Ezio Auditore", "ezio@ubisoft.com", 69
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(testCustomer));

        //When
        underTest.deleteCustomer(id);

        //Then
        Mockito.verify(customerDao).deleteCustomer(id);
    }

    @Test
    void deleteCustomerWillThrowWhenIdNotFound() {
        //Given
        int id = 7;

        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //When
        assertThatThrownBy(() -> underTest.deleteCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id)
                );


        //Then
        Mockito.verify(customerDao,Mockito.never()).deleteCustomer(id);

    }

    @Test
    void canUpdateAllCustomerProperties() {
        //Given
        int id = 7;
        Customer myCustomer = new Customer(
                id,"Ezio Auditore", "ezio@ubisoft.com", 69
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(myCustomer));

        String newEmail = "jamesCordon@abc.tv";
        CustomerUpdateRequest myUpdateRequest = new CustomerUpdateRequest(
                "Altair Something", newEmail, 42
        );
        Mockito.when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        //When
        underTest.updateCustomer(id, myUpdateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        Mockito.verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(myUpdateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(myUpdateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(myUpdateRequest.age());
    }

    @Test
    void canUpdateCustomerNameOnly() {
        //Given
        int id = 7;
        Customer myCustomer = new Customer(
                id,"Ezio Auditore", "ezio@ubisoft.com", 69
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(myCustomer));

        //String newEmail = "jamesCordon@abc.tv";
        CustomerUpdateRequest myUpdateRequest = new CustomerUpdateRequest(
                "Altair Something", null, null
        );


        //When
        underTest.updateCustomer(id, myUpdateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        Mockito.verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(myUpdateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(myCustomer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(myCustomer.getAge());

    }

    @Test
    void canUpdateCustomerEmailOnly() {
        //Given
        int id = 7;
        Customer myCustomer = new Customer(
                id,"Ezio Auditore", "ezio@ubisoft.com", 69
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(myCustomer));

        String newEmail = "jamesCordon@abc.tv";
        CustomerUpdateRequest myUpdateRequest = new CustomerUpdateRequest(
                null, newEmail, null
        );

        Mockito.when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        //When
        underTest.updateCustomer(id, myUpdateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        Mockito.verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(myCustomer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
        assertThat(capturedCustomer.getAge()).isEqualTo(myCustomer.getAge());
    }

    @Test
    void canUpdateCustomerAgeOnly() {
        //Given
        int id = 7;
        Customer myCustomer = new Customer(
                id,"Ezio Auditore", "ezio@ubisoft.com", 69
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(myCustomer));

        //String newEmail = "jamesCordon@abc.tv";
        CustomerUpdateRequest myUpdateRequest = new CustomerUpdateRequest(
                null, null, 44
        );

        //Mockito.when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        //When
        underTest.updateCustomer(id, myUpdateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        Mockito.verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(myCustomer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(myCustomer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(myUpdateRequest.age());
    }

    @Test
    void canUpdateCustomerEmailExceptionThrownWhenTaken() {
        //Given
        int id = 7;
        Customer myCustomer = new Customer(
                id,"Ezio Auditore", "ezio@ubisoft.com", 69
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(myCustomer));

        String newEmail = "jamesMay@ubisoft.com";
        CustomerUpdateRequest myUpdateRequest = new CustomerUpdateRequest(
                null, newEmail, null
        );

        Mockito.when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(true);
        //When
        //underTest.updateCustomer(id, myUpdateRequest);
        assertThatThrownBy(() -> underTest.updateCustomer(id, myUpdateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        //Then
        Mockito.verify(customerDao, Mockito.never()).updateCustomer(Mockito.any());

    }

    @Test
    void canUpdateWillThrowNoChanges() {
        //Given
        int id = 7;
        Customer myCustomer = new Customer(
                id,"Ezio Auditore", "ezio@ubisoft.com", 69
        );
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(myCustomer));

        //String newEmail = "jamesCordon@abc.tv";
        CustomerUpdateRequest myUpdateRequest = new CustomerUpdateRequest(
                myCustomer.getName(), myCustomer.getEmail(), myCustomer.getAge()
        );
        //Mockito.when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        //When
        assertThatThrownBy(() -> underTest.updateCustomer(id, myUpdateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        //Then


        Mockito.verify(customerDao, Mockito.never()).updateCustomer(Mockito.any());

    }


}