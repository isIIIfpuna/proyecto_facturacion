package com.fpuna.py.service;

import com.fpuna.py.entity.Installment;
import com.fpuna.py.model.response.InstallmentResponse;

import java.util.List;

public interface InstallmentService {

    public List<InstallmentResponse> getInstallmentsByInvoice(Integer invoiceId);
}
