package com.fpuna.py.service;

import com.fpuna.py.model.request.SaleRequest;
import com.fpuna.py.model.request.SaleUpdateRequest;
import com.fpuna.py.model.response.SaleResponse;

import java.util.List;

public interface SaleService {

    void createSale(SaleRequest saleRequest) throws Exception;

    void updateSale(SaleUpdateRequest saleUpdateRequest);

    void deleteSale(Integer saleId);

    SaleResponse getSale(Integer saleId);

    List<SaleResponse> getSales();
}
