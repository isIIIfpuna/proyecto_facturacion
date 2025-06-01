package com.fpuna.py.service;

import com.fpuna.py.model.exception.InvalidInstallmentException;
import com.fpuna.py.model.request.InvoiceRequest;
import com.fpuna.py.model.response.InvoiceResponse;

public interface InvoiceService {

    InvoiceResponse createInvoice(InvoiceRequest request) throws InvalidInstallmentException;

    InvoiceResponse getInvoice(Integer request);
}
