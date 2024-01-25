package com.esg.test.customer;

import org.springframework.data.repository.CrudRepository;

/**
 * Minimal repository implementations for Customer entities.
 * 
 * @author István Rátkai (Selindek)
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {
 
}
