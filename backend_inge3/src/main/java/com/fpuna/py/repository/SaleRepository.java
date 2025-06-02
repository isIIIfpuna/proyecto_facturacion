package com.fpuna.py.repository;

import com.fpuna.py.entity.Sale;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface SaleRepository extends CrudRepository<Sale, Serializable> {
}
