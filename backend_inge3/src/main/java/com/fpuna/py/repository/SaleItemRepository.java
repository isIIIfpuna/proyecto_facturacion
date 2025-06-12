package com.fpuna.py.repository;

import com.fpuna.py.entity.Customer;
import com.fpuna.py.entity.SaleItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface SaleItemRepository extends CrudRepository<SaleItem, Serializable> {

    @Query("SELECT S FROM SaleItem S WHERE S.sale.id = :s")
    List<SaleItem> findBySaleId(@Param("s") Integer saleId);
}
