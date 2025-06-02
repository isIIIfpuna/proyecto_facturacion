package com.fpuna.py.controller;

import com.fpuna.py.model.request.CustomerRequest;
import com.fpuna.py.model.request.CustomerUpdateRequest;
import com.fpuna.py.model.response.CustomerResponse;
import com.fpuna.py.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerLabels.TAG_CUSTOMER)
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    private static final String CUSTOMER_ID = "/{customer_id}";

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest request) {
        LOGGER.info("Received request to create customer: {}", request);
        try {
            customerService.createCustomer(request);
            LOGGER.info("Successfully created customer");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating customer", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateRequest request) {
        LOGGER.info("Received request to update customer: {}", request);
        try {
            customerService.updateCustomer(request);
            LOGGER.info("Successfully update customer");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updated customer", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = CUSTOMER_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCustomer(@PathVariable("customer_id") Integer customerId) {
        LOGGER.info("Received request to delete customer: {}", customerId);
        try {
            customerService.deleteCustomer(customerId);
            LOGGER.info("Successfully delete customer");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updated customer", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = CUSTOMER_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomerById(@PathVariable("customer_id") Integer customerId) {
        LOGGER.info("Received request to get customer: {}", customerId);
        try {
            CustomerResponse customerResponse = customerService.getCustomerById(customerId);
            LOGGER.info("Successfully get customer");
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error get customer", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomers() {
        try {
            List<CustomerResponse> customerResponseList = customerService.getCustomers();
            LOGGER.info("Successfully get customers");
            return new ResponseEntity<>(customerResponseList, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error get customer", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

