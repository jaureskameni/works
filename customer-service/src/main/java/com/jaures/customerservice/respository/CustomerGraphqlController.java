package com.jaures.customerservice.respository;

import com.jaures.customerservice.entities.Customer;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerGraphqlController {

    private final CustomerRepository customerRepository;

    @QueryMapping
    public List<Customer> allCustomers() {
        return customerRepository.findAll();
    }

    @QueryMapping
    public Customer customerById(@Argument Long id) {
        return customerRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Customer not found"));

    }

    @MutationMapping
    public Customer saveCustomer(@Argument Customer customer){
        return customerRepository.save(customer);
    }
}
