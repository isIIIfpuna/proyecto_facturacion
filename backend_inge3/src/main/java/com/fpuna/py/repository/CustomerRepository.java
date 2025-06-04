package com.fpuna.py.repository;

import com.fpuna.py.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

public interface CustomerRepository extends CrudRepository<Customer, Serializable> {

    @Query("SELECT C FROM Customer C WHERE C.email = :e")
    Customer findByEmail(@Param("e") String email);
}
