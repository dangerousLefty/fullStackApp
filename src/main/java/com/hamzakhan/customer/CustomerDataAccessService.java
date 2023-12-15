package com.hamzakhan.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDataAccessService implements CustomerDAO {

    //db
    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();

        Customer alex = new Customer(1, "Alex", "alex@gmail.com", 12);
        Customer jamila = new Customer(2, "Jamila", "jamila@amigoscode.com", 25);

        customers.add(alex);
        customers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> getCustomerByID(Integer cID) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(cID))
                .findFirst();
    }
}
