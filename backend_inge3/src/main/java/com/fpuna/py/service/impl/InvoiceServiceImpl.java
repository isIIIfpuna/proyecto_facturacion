package com.fpuna.py.service.impl;

import com.fpuna.py.entity.Customer;
import com.fpuna.py.entity.Invoice;
import com.fpuna.py.model.request.InvoiceRequest;
import com.fpuna.py.model.request.InvoiceUpdateRequest;
import com.fpuna.py.model.response.InvoiceResponse;
import com.fpuna.py.repository.CustomerRepository;
import com.fpuna.py.repository.InstallmentRepository;
import com.fpuna.py.repository.InvoiceRepository;
import com.fpuna.py.service.InvoiceService;
import com.fpuna.py.util.MethodUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    final CustomerRepository customerRepository;

    final InvoiceRepository invoiceRepository;

    final InstallmentRepository installmentRepository;

    public InvoiceServiceImpl(CustomerRepository customerRepository, InvoiceRepository invoiceRepository, InstallmentRepository installmentRepository) {
        this.customerRepository = customerRepository;
        this.invoiceRepository = invoiceRepository;
        this.installmentRepository = installmentRepository;
    }

    @Override
    public void createInvoice(InvoiceRequest request) throws Exception {
        Optional<Customer> customerOpt = customerRepository.findById(request.getCustomerId());
        if (customerOpt.isEmpty()) return;

        if (Objects.nonNull(request.getInstallmentCount()) && request.getInstallmentDays().split(",").length != request.getInstallmentCount()) {
            throw new Exception("The number of installment days must match the installment count");
        }

        Invoice invoice = new Invoice();
        invoice.setCustomer(customerOpt.get());
        invoice.setDate(LocalDate.now());
        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setPaymentType(request.getPaymentType());
        invoice.setInstallmentCount(request.getInstallmentCount());
        invoice.setInstallmentDays(request.getInstallmentDays());
        invoiceRepository.save(invoice);
    }

    @Override
    public void updateInvoice(InvoiceUpdateRequest invoiceUpdateRequest) {
        invoiceRepository.findById(invoiceUpdateRequest.getInvoiceId()).ifPresent(invoice -> {
            invoice.setDate(LocalDate.now());
            invoice.setTotalAmount(invoiceUpdateRequest.getTotalAmount());
            invoice.setPaymentType(invoiceUpdateRequest.getPaymentType());
            invoice.setInstallmentCount(invoiceUpdateRequest.getInstallmentCount());
            invoice.setInstallmentDays(invoiceUpdateRequest.getInstallmentDays());
            invoiceRepository.save(invoice);
        });
    }

    @Override
    public void deleteInvoice(Integer invoiceId) {
        invoiceRepository.findById(invoiceId).ifPresent(invoice -> {
            invoiceRepository.delete(invoice);
        });
    }

    @Override
    public InvoiceResponse getInvoice(Integer invoiceId) {
        return invoiceRepository.findById(invoiceId).map(invoice -> {
            InvoiceResponse invoiceResponse = new InvoiceResponse();
            invoiceResponse.setInvoiceId(invoiceId);
            invoiceResponse.setDate(MethodUtils.convertLocalDateToString(invoice.getDate()));
            invoiceResponse.setTotalAmount(invoice.getTotalAmount());
            invoiceResponse.setPaymentType(invoice.getPaymentType());
            invoiceResponse.setInstallmentCount(invoice.getInstallmentCount());
            invoiceResponse.setInstallmentDays(invoice.getInstallmentDays());
            return invoiceResponse;
        }).orElse(null);
    }

    @Override
    public List<InvoiceResponse> getInvoices() {
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        invoiceRepository.findAll().forEach(invoice -> {
            InvoiceResponse invoiceResponse = new InvoiceResponse();
            invoiceResponse.setInvoiceId(invoice.getId());
            invoiceResponse.setDate(MethodUtils.convertLocalDateToString(invoice.getDate()));
            invoiceResponse.setTotalAmount(invoice.getTotalAmount());
            invoiceResponse.setPaymentType(invoice.getPaymentType());
            invoiceResponse.setInstallmentCount(invoice.getInstallmentCount());
            invoiceResponse.setInstallmentDays(invoice.getInstallmentDays());
            invoiceResponseList.add(invoiceResponse);
        });
        return invoiceResponseList;
    }
}
