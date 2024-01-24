package com.hamzakhan.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDAO {

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

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(e -> e.getEmail().equals(email));
    }

    @Override
    public void deleteCustomerById(Integer cID) {
        customers.stream()
            .filter(c -> c.getId().equals(cID))
            .findFirst()
            .ifPresent(c -> customers.remove(c));
    }

    @Override
    public boolean existsCustomerById(Integer cID) {
        return customers.stream()
                .anyMatch(e -> e.getId().equals(cID));
    }

    @Override
    public void updateCustomer(Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCustomer'");
    }

}
