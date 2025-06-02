package com.fpuna.py.repository;

import com.fpuna.py.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface ProductRepository extends CrudRepository<Product, Serializable> {
}
