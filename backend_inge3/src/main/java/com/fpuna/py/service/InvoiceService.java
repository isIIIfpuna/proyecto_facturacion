package com.fpuna.py.service;

import com.fpuna.py.model.request.InvoiceRequest;
import com.fpuna.py.model.request.InvoiceUpdateRequest;
import com.fpuna.py.model.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {

    void createInvoice(InvoiceRequest invoiceRequest) throws Exception;

    void updateInvoice(InvoiceUpdateRequest invoiceUpdateRequest);

    void deleteInvoice(Integer invoiceId);

    InvoiceResponse getInvoice(Integer invoiceId);

    List<InvoiceResponse> getInvoices();
}
