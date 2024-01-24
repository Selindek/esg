package com.esg.test.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
class CustomerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;
  
  @Test
  void whenPostingCustomerThenStatusIsCreated() throws JsonProcessingException, Exception {
    Customer customer = new Customer();
    customer.setCustomerRef(UUID.randomUUID().toString());
    customer.setCustomerName("Tom");
    
    mockMvc.perform(post("/customers")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(customer)))
        .andExpect(status().isCreated());
  }
  
  @Test
  void whenPostingCustomerWithoutRefThenStatusIsBadRequest() throws JsonProcessingException, Exception {
    Customer customer = new Customer();
    customer.setCustomerName("Fred");
    
    mockMvc.perform(post("/customers")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(customer)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void whenPostingCustomerWithExistingRefThenStatusIsConflict() throws JsonProcessingException, Exception {
    Customer customer = new Customer();
    customer.setCustomerRef(UUID.randomUUID().toString());
    customer.setCustomerName("Tim");

    // existing ref
    mockMvc.perform(post("/customers")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(customer)))
        .andExpect(status().isCreated());

    mockMvc.perform(post("/customers")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(customer)))
        .andExpect(status().isConflict());
    
  }

  @Test
  void whenGettingCustomerListThenStatusIsOk() throws JsonProcessingException, Exception {
    
    mockMvc.perform(get("/customers"))
        .andDo(print())
        .andExpect(status().isOk());
  }
  
  @Test
  void whenGettingNonExistingCusomerThenStatusIsNotFound() throws JsonProcessingException, Exception {
    
    mockMvc.perform(get("/customers/{ref}",UUID.randomUUID().toString()))
        .andExpect(status().isNotFound());
  }

  @Test
  void whenGettingExistingCusomerThenResponseIsExpected() throws JsonProcessingException, Exception {
    
    // existing customer
    var ref = UUID.randomUUID().toString();
    Customer customer = new Customer();
    customer.setCustomerRef(ref);
    customer.setCustomerName("Tom");
    
    mockMvc.perform(post("/customers")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(customer)))
        .andExpect(status().isCreated());
    
    mockMvc.perform(get("/customers/{ref}",ref))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerRef").value(ref))
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Tom"));
        
  }

}
