package com.fpuna.py.controller;

import com.fpuna.py.model.request.ProductRequest;
import com.fpuna.py.model.request.ProductUpdateRequest;
import com.fpuna.py.model.response.ProductResponse;
import com.fpuna.py.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerLabels.TAG_PRODUCT)
public class ProductController {

    private static final Logger LOGGER = LogManager.getLogger(ProductController.class);

    private static final String PRODUCT_ID = "/{product_id}";

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        LOGGER.info("Received request to create product: {}", request);
        try {
            productService.createProduct(request);
            LOGGER.info("Successfully created product");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating product", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequest request) {
        LOGGER.info("Received request to update product: {}", request);
        try {
            productService.updateProduct(request);
            LOGGER.info("Successfully update product");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updated product", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = PRODUCT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable("product_id") Integer productId) {
        LOGGER.info("Received request to delete product: {}", productId);
        try {
            productService.deleteProduct(productId);
            LOGGER.info("Successfully delete product");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updated product", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = PRODUCT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable("product_id") Integer productId) {
        LOGGER.info("Received request to get product: {}", productId);
        try {
            ProductResponse productResponse = productService.getProductById(productId);
            LOGGER.info("Successfully get product");
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error get product", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProducts() {
        try {
            List<ProductResponse> productResponseList = productService.getProducts();
            LOGGER.info("Successfully get products");
            return new ResponseEntity<>(productResponseList, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error get product", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

