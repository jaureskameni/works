package com.jaures.customerservice;

import com.jaures.customerservice.entities.Customer;
import com.jaures.customerservice.respository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CustomerServiceApplication.class, args);

	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepo){
		return args -> {
			customerRepo.save(Customer.builder()
							.name("Jaures Customer")
							.email("jaures@gmail.com")
					.build());

			customerRepo.save(Customer.builder()
							.name("kameni Customer")
							.email("kameni@gmail.com")
					.build());

					customerRepo.save(Customer.builder()
							.name("guimel Customer")
							.email("guimel@gmail.com")
					.build());
		};

	}

}
