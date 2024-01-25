package com.esg.test.customer;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * <p>
 * Minimal API implementation for Customers.
 * </p>
 * Please note, that the service layer is skipped for simplicity.
 * 
 * @author István Rátkai (Selindek)
 *
 */
@RestController
@RequestMapping(value = "/customers")
@AllArgsConstructor
public class CustomerController {

  private final CustomerRepository customerRepository;

  
  @GetMapping
  Iterable<Customer> getCustomers() {
    return customerRepository.findAll();
  }

  @GetMapping("{ref}")
  ResponseEntity<Customer> getCustomer(@PathVariable String ref) {
    
    return customerRepository.findById(ref).map(c-> new ResponseEntity<>(c, HttpStatus.OK))
        .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  ResponseEntity<Object> postCustomer(@Valid @RequestBody Customer customer) {
    
    if(customerRepository.existsById(customer.getCustomerRef())) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    
    customerRepository.save(customer);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }


}
