package com.hamzaKhan.fullStackbackEnd.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

//    ---------- constructed using JPQL ----------
    boolean existsCustomerByEmail(String email);
}
