package com.fpuna.py.repository;

import com.fpuna.py.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface InvoiceRepository extends CrudRepository<Invoice, Serializable> {
}
