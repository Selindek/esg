package com.esg.test.customer;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

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
  Customer getCustomers(@PathVariable String ref) {
    return customerRepository.getByCustomerRef(ref).orElseThrow(NotFoundException::new);
  }

  @PostMapping
  ResponseEntity<Void> getCustomers(@RequestBody Customer customer) {
    if(StringUtils.isBlank(customer.getCustomerRef())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if(customerRepository.existsById(customer.getCustomerRef())) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    customerRepository. save(customer);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
  

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity Not Found")
  public static class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
  }
}
