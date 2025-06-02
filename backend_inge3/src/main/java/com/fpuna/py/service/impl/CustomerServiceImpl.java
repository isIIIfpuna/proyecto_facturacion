package com.fpuna.py.service.impl;

import com.fpuna.py.entity.Customer;
import com.fpuna.py.model.request.CustomerRequest;
import com.fpuna.py.model.request.CustomerUpdateRequest;
import com.fpuna.py.model.response.CustomerResponse;
import com.fpuna.py.repository.CustomerRepository;
import com.fpuna.py.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void createCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        customer.setCiRuc(customerRequest.getCiRuc());
        customer.setAddress(customerRequest.getAddress());
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        customerRepository.findById(customerUpdateRequest.getCustomerId()).ifPresent(customer -> {
            customer.setName(customerUpdateRequest.getName());
            customer.setEmail(customerUpdateRequest.getEmail());
            customer.setPhone(customerUpdateRequest.getPhone());
            customer.setCiRuc(customerUpdateRequest.getCiRuc());
            customer.setAddress(customerUpdateRequest.getAddress());
            customerRepository.save(customer);
        });
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        customerRepository.findById(customerId).ifPresent(customerRepository::delete);
    }

    @Override
    public CustomerResponse getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    CustomerResponse customerResponse = new CustomerResponse();
                    customerResponse.setCustomerId(customerId);
                    customerResponse.setName(customer.getName());
                    customerResponse.setEmail(customer.getEmail());
                    customerResponse.setPhone(customer.getPhone());
                    customerResponse.setCiRuc(customer.getCiRuc());
                    customerResponse.setAddress(customer.getAddress());
                    return customerResponse;
                })
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + customerId));
    }

    @Override
    public List<CustomerResponse> getCustomers() {
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setCustomerId(customer.getId());
            customerResponse.setName(customer.getName());
            customerResponse.setEmail(customer.getEmail());
            customerResponse.setPhone(customer.getPhone());
            customerResponse.setCiRuc(customer.getCiRuc());
            customerResponse.setAddress(customer.getAddress());
            customerResponseList.add(customerResponse);
        });
        return customerResponseList;
    }
}
