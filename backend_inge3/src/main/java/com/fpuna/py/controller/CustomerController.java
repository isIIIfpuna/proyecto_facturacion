package com.fpuna.py.controller;

import com.fpuna.py.model.request.CustomerRequest;
import com.fpuna.py.model.response.CustomerResponse;
import com.fpuna.py.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(ControllerLabels.TAG_CUSTOMER)
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    private static final String FIND_CUSTOMER_BY_ID = "/{id}";

    @Autowired
    CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        LOGGER.info("Received request to create customer: {}", request);
        try {
            CustomerResponse response = customerService.createCustomer(request);
            LOGGER.info("Successfully created customer with ID: {}", response.getCustomerId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error("Error creating customer", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = FIND_CUSTOMER_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Integer id) {
        LOGGER.info("Received request to get customer with ID: {}", id);
        try {
            CustomerResponse response = customerService.getCustomer(id);
            if (Objects.isNull(response)) {
                LOGGER.warn("Customer with ID {} not found", id);
                return ResponseEntity.notFound().build();
            }
            LOGGER.info("Successfully retrieved customer with ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error("Error retrieving customer with ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }


}

