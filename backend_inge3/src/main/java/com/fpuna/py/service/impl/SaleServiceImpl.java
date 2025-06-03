package com.fpuna.py.service.impl;

import com.fpuna.py.entity.Sale;
import com.fpuna.py.model.request.SaleRequest;
import com.fpuna.py.model.request.SaleUpdateRequest;
import com.fpuna.py.model.response.CustomerResponse;
import com.fpuna.py.model.response.SaleResponse;
import com.fpuna.py.repository.CustomerRepository;
import com.fpuna.py.repository.SaleRepository;
import com.fpuna.py.service.SaleService;
import com.fpuna.py.util.MethodUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    final SaleRepository saleRepository;

    final CustomerRepository customerRepository;

    public SaleServiceImpl(SaleRepository saleRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void createSale(SaleRequest saleRequest) {
        Sale sale = new Sale();
        sale.setCustomer(customerRepository.findById(saleRequest.getCustomerId()).get());
        sale.setSaleDate(LocalDate.now());
        sale.setTotalAmount(saleRequest.getTotalAmount());
        sale.setPaymentType(saleRequest.getPaymentType());
        saleRepository.save(sale);
    }

    @Override
    public void updateSale(SaleUpdateRequest saleUpdateRequest) {
        saleRepository.findById(saleUpdateRequest.getSaleId()).ifPresent(sale -> {
            sale.setPaymentType(saleUpdateRequest.getPaymentType());
            sale.setTotalAmount(saleUpdateRequest.getTotalAmount());
            saleRepository.save(sale);
        });
    }

    @Override
    public void deleteSale(Integer saleId) {
        saleRepository.findById(saleId).ifPresent(sale -> {
            saleRepository.delete(sale);
        });
    }

    @Override
    public SaleResponse getSale(Integer saleId) {
        return saleRepository.findById(saleId).map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(saleId);
            saleResponse.setPaymentType(sale.getPaymentType());
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setSaleDate(MethodUtils.convertLocalDateToString(sale.getSaleDate()));
            saleResponse.setCustomer(new CustomerResponse(sale.getCustomer().getId(), sale.getCustomer().getName(), sale.getCustomer().getCiRuc(),
                    sale.getCustomer().getEmail(), sale.getCustomer().getPhone(), sale.getCustomer().getAddress()));
            return saleResponse;
        }).orElse(null);
    }

    @Override
    public List<SaleResponse> getSales() {
        List<SaleResponse> saleResponseList = new ArrayList<>();
        saleRepository.findAll().forEach(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getId());
            saleResponse.setPaymentType(sale.getPaymentType());
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setSaleDate(MethodUtils.convertLocalDateToString(sale.getSaleDate()));
            saleResponse.setCustomer(new CustomerResponse(sale.getCustomer().getId(), sale.getCustomer().getName(), sale.getCustomer().getCiRuc(),
                    sale.getCustomer().getEmail(), sale.getCustomer().getPhone(), sale.getCustomer().getAddress()));
            saleResponseList.add(saleResponse);
        });
        return saleResponseList;
    }
}
