package com.hamzaKhan.fullStackbackEnd;

import com.hamzaKhan.fullStackbackEnd.customer.Customer;
import com.hamzaKhan.fullStackbackEnd.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository) {
		//return args -> {};
		return args -> {
			Customer alex = new Customer(
					1,
					"Alex",
					"alex@gmail.com",
					21
			);

			Customer jamila = new Customer(
					2,
					"Jamila",
					"jamila@gmail.com",
					19
			);

			List<Customer> customers = List.of(alex, jamila);
			customerRepository.saveAll(customers);

		};

	}

	}
