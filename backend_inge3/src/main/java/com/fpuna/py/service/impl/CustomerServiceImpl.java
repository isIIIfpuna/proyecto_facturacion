package com.fpuna.py.service.impl;

import com.fpuna.py.controller.CustomerController;
import com.fpuna.py.entity.Customer;
import com.fpuna.py.model.request.CustomerRequest;
import com.fpuna.py.model.response.CustomerResponse;
import com.fpuna.py.repository.CustomerRepository;
import com.fpuna.py.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        customer.setCiRuc(customerRequest.getCi_ruc());
        customer.setAddress(customerRequest.getAddress());
        customer = customerRepository.save(customer);
        return new CustomerResponse(customer.getId(), customer.getAddress(), customer.getEmail(), customer.getName(), customer.getPhone(), customer.getCiRuc());
    }

    @Override
    public CustomerResponse getCustomer(Integer customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) return null;
        Customer customer = customerOpt.get();
        return new CustomerResponse(customer.getId(), customer.getAddress(), customer.getEmail(), customer.getName(), customer.getPhone(), customer.getCiRuc());
    }

}
