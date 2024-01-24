package com.hamzakhan;

import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.javafaker.Faker;
import com.hamzakhan.customer.Customer;
import com.hamzakhan.customer.CustomerRepository;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }


    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Faker myFaker = new Faker();
            Random myRand = new Random();
            Customer myCustomer = new Customer(myFaker.name().firstName() , myFaker.internet().emailAddress(), myRand.nextInt(29) + 10);

            /*List<Customer> customers = List.of(alex, jamila);
            customerRepository.saveAll(customers);*/
            customerRepository.save(myCustomer);
        };
    }
}
