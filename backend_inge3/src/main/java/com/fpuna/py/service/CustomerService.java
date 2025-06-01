package com.fpuna.py.service;

import com.fpuna.py.model.request.CustomerRequest;
import com.fpuna.py.model.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse getCustomer(Integer customerId);
}
