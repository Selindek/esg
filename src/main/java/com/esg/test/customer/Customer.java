package com.esg.test.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Minimal implementation for Customers entity.
 * </p>
 * In real life scenario additional validation annotations should be added 
 * to the properties.
 * 
 * @author István Rátkai (Selindek)
 *
 */
@Entity
@Getter
@Setter
public class Customer {

  @Id
  @NotEmpty
  private String customerRef;
  private String customerName;
  private String addressLine1;
  private String addressLine2;
  private String town;
  private String county;
  private String country;
  private String postcode;
  
}
