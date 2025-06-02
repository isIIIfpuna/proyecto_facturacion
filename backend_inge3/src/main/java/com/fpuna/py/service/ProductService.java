package com.fpuna.py.service;

import com.fpuna.py.model.request.ProductRequest;
import com.fpuna.py.model.request.ProductUpdateRequest;
import com.fpuna.py.model.response.ProductResponse;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequest productRequest);

    void updateProduct(ProductUpdateRequest productUpdateRequest);

    void deleteProduct(Integer productId);

    ProductResponse getProductById(Integer productId);

    List<ProductResponse> getProducts();
}
