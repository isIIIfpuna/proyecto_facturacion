package com.fpuna.py.service;

import com.fpuna.py.model.response.InstallmentResponse;

import java.util.List;

public interface InstallmentService {

    List<InstallmentResponse> getInstallmentsByInvoice(Integer invoiceId);
}
