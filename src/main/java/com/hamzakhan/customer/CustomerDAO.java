package com.hamzakhan.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> selectAllCustomers();
    Optional<Customer> getCustomerByID(Integer cID);
    void insertCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomerById(Integer cID);
    boolean existsPersonWithEmail(String email);
    boolean existsCustomerById(Integer cID);
    
    //boolean changesPresent(CustomerUpdateRequest customerUpdateRequest);
    //void updateCustomerById(Integer cID, Customer customer);
    //Customer fetchCustomer(Integer cID);
}
