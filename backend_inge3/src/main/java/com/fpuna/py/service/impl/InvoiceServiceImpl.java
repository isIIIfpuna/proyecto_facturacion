package com.fpuna.py.service.impl;

import com.fpuna.py.entity.Customer;
import com.fpuna.py.entity.Invoice;
import com.fpuna.py.model.exception.InvalidInstallmentException;
import com.fpuna.py.model.request.InvoiceRequest;
import com.fpuna.py.model.response.InvoiceResponse;
import com.fpuna.py.repository.CustomerRepository;
import com.fpuna.py.repository.InstallmentRepository;
import com.fpuna.py.repository.InvoiceRepository;
import com.fpuna.py.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InstallmentRepository installmentRepository;

    @Override
    public InvoiceResponse createInvoice(InvoiceRequest request) throws InvalidInstallmentException {
        Optional<Customer> customerOpt = customerRepository.findById(request.getCustomerId());
        if (customerOpt.isEmpty()) return null;

        if (Objects.nonNull(request.getInstallmentCount())
                && request.getInstallmentDays().split(",").length != request.getInstallmentCount()) {
            throw new InvalidInstallmentException("The number of installment days must match the installment count");
        }

        Invoice invoice = new Invoice();
        invoice.setCustomer(customerOpt.get());
        invoice.setDate(LocalDate.now());
        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setPaymentType(request.getPaymentType());
        invoice.setInstallmentCount(request.getInstallmentCount());
        invoice.setInstallmentDays(request.getInstallmentDays());
        invoice = invoiceRepository.save(invoice);
        return new InvoiceResponse(invoice.getId(), invoice.getCustomer().getId(), invoice.getDate(), invoice.getTotalAmount(), invoice.getPaymentType(), invoice.getInstallmentCount(), "CREATED");
    }

    @Override
    public InvoiceResponse getInvoice(Integer invoiceId) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        if (invoiceOpt.isEmpty()) return null;
        Invoice invoice = invoiceOpt.get();
        return new InvoiceResponse(invoice.getId(), invoice.getCustomer().getId(), invoice.getDate(), invoice.getTotalAmount(), invoice.getPaymentType(), invoice.getInstallmentCount(), "RETRIEVED");
    }
}
