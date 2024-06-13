package com.hamzaKhan.fullStackbackEnd;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.hamzaKhan.fullStackbackEnd.customer.Customer;
import com.hamzaKhan.fullStackbackEnd.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);


		//test line oniichan
	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository) {
		//return args -> {};
		return args -> {

			Faker faker = new Faker();
			Name customerName = faker.name();
			String email = faker.internet().emailAddress();
			int age = (int) (Math.random() * 100) % 50;
			String firstName = customerName.firstName();
			String lastName = customerName.lastName();
			Customer toAdd = new Customer(
					firstName + " " + lastName,
					firstName.toLowerCase() +
							"_" + lastName.toLowerCase() +
							"@yahoo.com",
					age
			);

			/*Customer jamila = new Customer(
					2,
					"Jamila",
					"jamila@gmail.com",
					19
			);*/

			//List<Customer> customers = List.of(alex, jamila);
			customerRepository.save(toAdd);

		};

	}

	}
