package com.esg.test.customer;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {

  Optional<Customer> getByCustomerRef(String ref);
}
