package com.fpuna.py.service.impl;

import com.fpuna.py.entity.Customer;
import com.fpuna.py.entity.Sale;
import com.fpuna.py.entity.SaleItem;
import com.fpuna.py.model.request.SaleItemRequest;
import com.fpuna.py.model.request.SaleItemUpdateRequest;
import com.fpuna.py.model.request.SaleRequest;
import com.fpuna.py.model.request.SaleUpdateRequest;
import com.fpuna.py.model.response.CustomerResponse;
import com.fpuna.py.model.response.SaleItemResponse;
import com.fpuna.py.model.response.SaleResponse;
import com.fpuna.py.repository.CustomerRepository;
import com.fpuna.py.repository.ProductRepository;
import com.fpuna.py.repository.SaleItemRepository;
import com.fpuna.py.repository.SaleRepository;
import com.fpuna.py.service.SaleService;
import com.fpuna.py.util.MethodUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    final SaleRepository saleRepository;
    final SaleItemRepository saleItemRepository;
    final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public SaleServiceImpl(SaleRepository saleRepository, SaleItemRepository saleItemRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createSale(SaleRequest saleRequest) {
        Sale sale = new Sale();
        Customer customer = customerRepository.findByEmail(saleRequest.getCustomer().getEmail());
        if (customer == null) {
            customer = new Customer();
            customer.setEmail(saleRequest.getCustomer().getEmail());
            customer.setName(saleRequest.getCustomer().getName());
            customer.setPhone(saleRequest.getCustomer().getPhone());
            customer.setAddress(saleRequest.getCustomer().getAddress());
            customer.setCiRuc(saleRequest.getCustomer().getCiRuc());
            customer.setCreatedAt(Instant.now());
            customerRepository.save(customer);
        }
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());
        sale.setTotalAmount(saleRequest.getTotalAmount());
        sale.setPaymentType(saleRequest.getPaymentType());
        sale.setInstallmentCount(saleRequest.getInstallments().getInstallments());
        if (saleRequest.getInstallments().getInstallmentDays() != null && !saleRequest.getInstallments().getInstallmentDays().isEmpty()) {
            String days = saleRequest.getInstallments().getInstallmentDays().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            sale.setInstallmentDays(days);
        }
        saleRepository.save(sale);

        if (Objects.nonNull(saleRequest.getSaleItems())) {
            for (SaleItemRequest saleItemRequest : saleRequest.getSaleItems()) {
                SaleItem saleItem = new SaleItem();
                saleItem.setSale(sale);
                saleItem.setQuantity(saleItemRequest.getQuantity());
                saleItem.setSubtotal(saleItemRequest.getSubtotal());
                saleItem.setUnitPrice(saleItemRequest.getUnitPrice());
                saleItem.setProduct(productRepository.findById(saleItemRequest.getProductId()).get());
                saleItemRepository.save(saleItem);
            }
        }
    }

    @Override
    public void updateSale(SaleUpdateRequest saleUpdateRequest) {
        saleRepository.findById(saleUpdateRequest.getSaleId()).ifPresent(sale -> {
            sale.setPaymentType(saleUpdateRequest.getPaymentType());
            sale.setTotalAmount(saleUpdateRequest.getTotalAmount());
            sale.setCustomer(sale.getCustomer());
            sale.setSaleDate(LocalDate.now());
            sale.setId(saleUpdateRequest.getSaleId());
            sale.setInstallmentCount(saleUpdateRequest.getInstallments().getInstallments());
            if (saleUpdateRequest.getInstallments().getInstallmentDays() != null && !saleUpdateRequest.getInstallments().getInstallmentDays().isEmpty()) {
                String days = saleUpdateRequest.getInstallments().getInstallmentDays().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                sale.setInstallmentDays(days);
            }
            saleRepository.save(sale);
            if (Objects.nonNull(saleUpdateRequest.getSaleItemUpdateList())) {
                for (SaleItemUpdateRequest saleItemUpdateRequest : saleUpdateRequest.getSaleItemUpdateList()) {
                    if (Objects.nonNull(saleItemUpdateRequest.getSaleItemId())) {
                        SaleItem saleItem = saleItemRepository.findById(saleItemUpdateRequest.getSaleItemId()).get();
                        saleItem.setId(saleItemUpdateRequest.getSaleItemId());
                        saleItem.setSale(sale);
                        saleItem.setQuantity(saleItemUpdateRequest.getQuantity());
                        saleItem.setSubtotal(saleItemUpdateRequest.getSubtotal());
                        saleItem.setUnitPrice(saleItemUpdateRequest.getUnitPrice());
                        saleItem.setProduct(productRepository.findById(saleItemUpdateRequest.getProductId()).get());
                        saleItemRepository.save(saleItem);
                    } else {
                        SaleItem saleItem = new SaleItem();
                        saleItem.setSale(sale);
                        saleItem.setQuantity(saleItemUpdateRequest.getQuantity());
                        saleItem.setSubtotal(saleItemUpdateRequest.getSubtotal());
                        saleItem.setUnitPrice(saleItemUpdateRequest.getUnitPrice());
                        saleItem.setProduct(productRepository.findById(saleItemUpdateRequest.getProductId()).get());
                        saleItemRepository.save(saleItem);
                    }
                }
            }
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
            List<SaleItem> saleItems = saleItemRepository.findBySaleId(saleId);
            List<SaleItemResponse> saleItemResponses = getSaleItemResponses(saleItems);
            saleResponse.setInstallmentCount(sale.getInstallmentCount());
            if (Objects.nonNull(sale.getInstallmentDays())) {
                String days = sale.getInstallmentDays();
                List<Integer> installmentDays = Arrays.stream(days.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                saleResponse.setInstallmentDays(installmentDays);
            }
            saleResponse.setSaleItemResponse(saleItemResponses);
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

    private static List<SaleItemResponse> getSaleItemResponses(List<SaleItem> saleItems) {
        List<SaleItemResponse> saleItemResponses = new ArrayList<>();
        for (SaleItem saleItem : saleItems) {
            SaleItemResponse saleItemResponse = new SaleItemResponse();
            saleItemResponse.setSaleItemId(saleItem.getId());
            saleItemResponse.setQuantity(saleItem.getQuantity());
            saleItemResponse.setSubtotal(saleItem.getSubtotal());
            saleItemResponse.setUnitPrice(saleItem.getUnitPrice());
            saleItemResponse.setProductId(saleItem.getProduct().getId());
            saleItemResponse.setProductName(saleItem.getProduct().getName());
            saleItemResponses.add(saleItemResponse);
        }
        return saleItemResponses;
    }
}
