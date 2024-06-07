package com.hamzaKhan.fullStackbackEnd.customer;

import com.hamzaKhan.fullStackbackEnd.exception.DuplicateResourceException;
import com.hamzaKhan.fullStackbackEnd.exception.RequestValidationException;
import com.hamzaKhan.fullStackbackEnd.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistration){
        //check if email exists
        String email = customerRegistration.email();
        if (customerDao.existsPersonWithEmail(email))
            throw new DuplicateResourceException("email already taken");

        //add
        Customer customer = new Customer(
                customerRegistration.name(),
                email,
                customerRegistration.age()
        );
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomer(Integer id){
        getCustomer(id);

        customerDao.deleteCustomer(id);
    }

    public void updateCustomer(Integer id,CustomerUpdateRequest updateRequest){
        Customer customer = getCustomer(id);
        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }
}
