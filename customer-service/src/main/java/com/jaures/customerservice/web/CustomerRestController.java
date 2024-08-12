package com.jaures.customerservice.web;

import com.jaures.customerservice.entities.Customer;
import com.jaures.customerservice.respository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class CustomerRestController {

    private final CustomerRepository customerRepository;

    @PostMapping("/addCustomer")
    public Customer addCustomer(@RequestBody Customer customer){
        customerRepository.save(customer);
        return customer;
    }

    @GetMapping("/customers")
    public List<Customer> customerList() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{idCustomer}")
    public Customer customerGyId(@PathVariable("idCustomer") Long idCustomer){
        return customerRepository.findById(idCustomer).get();
    }
}
