package com.fpuna.py.repository;

import com.fpuna.py.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface CustomerRepository extends CrudRepository<Customer, Serializable> {
}
