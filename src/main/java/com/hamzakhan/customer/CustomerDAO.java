package com.hamzakhan.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> selectAllCustomers();
    Optional<Customer> getCustomerByID(Integer cID);
}
