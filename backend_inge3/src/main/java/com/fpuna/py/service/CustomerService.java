package com.fpuna.py.service;

import com.fpuna.py.model.request.CustomerRequest;
import com.fpuna.py.model.request.CustomerUpdateRequest;
import com.fpuna.py.model.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    void createCustomer(CustomerRequest customerRequest);

    void updateCustomer(CustomerUpdateRequest customerUpdateRequest);

    void deleteCustomer(Integer customerId);

    CustomerResponse getCustomerById(Integer customerId);

    List<CustomerResponse> getCustomers();
}
