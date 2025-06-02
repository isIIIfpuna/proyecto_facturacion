package com.fpuna.py.repository;

import com.fpuna.py.entity.SaleItem;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface SaleItemRepository extends CrudRepository<SaleItem, Serializable> {
}
