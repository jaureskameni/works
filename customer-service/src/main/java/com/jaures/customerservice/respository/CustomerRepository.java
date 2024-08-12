package com.jaures.customerservice.respository;

import com.jaures.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {
}
