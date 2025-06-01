package com.fpuna.py.repository;

import com.fpuna.py.entity.Installment;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;

public interface InstallmentRepository extends CrudRepository<Installment, Serializable> {

    List<Installment> findByInvoiceId(Integer invoiceId);
}
