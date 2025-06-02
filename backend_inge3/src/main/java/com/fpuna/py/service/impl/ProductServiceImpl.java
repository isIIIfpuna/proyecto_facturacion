package com.fpuna.py.service.impl;

import com.fpuna.py.entity.Product;
import com.fpuna.py.model.request.ProductRequest;
import com.fpuna.py.model.request.ProductUpdateRequest;
import com.fpuna.py.model.response.ProductResponse;
import com.fpuna.py.repository.ProductRepository;
import com.fpuna.py.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductUpdateRequest productUpdateRequest) {
        productRepository.findById(productUpdateRequest.getProductId()).ifPresent(product -> {
            product.setName(productUpdateRequest.getName());
            product.setDescription(productUpdateRequest.getDescription());
            product.setPrice(productUpdateRequest.getPrice());
            product.setStock(productUpdateRequest.getStock());
            productRepository.save(product);
        });
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.findById(productId).ifPresent(product -> {
            productRepository.delete(product);
        });
    }

    @Override
    public ProductResponse getProductById(Integer productId) {
        return productRepository.findById(productId).map(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductId(productId);
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setStock(product.getStock());
            return productResponse;
        }).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + productId));
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<ProductResponse> productResponses = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setStock(product.getStock());
            productResponses.add(productResponse);
        });
        return productResponses;
    }
}
