package com.fpuna.py.repository;

import com.fpuna.py.entity.Installment;
import com.fpuna.py.entity.SaleItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface InstallmentRepository extends CrudRepository<Installment, Serializable> {

    @Query("SELECT S FROM Installment S WHERE S.sale.id = :s")
    List<Installment> findBySaleId(@Param("s") Integer saleId);}
