package com.fpuna.py.controller;

import com.fpuna.py.model.request.SaleRequest;
import com.fpuna.py.model.request.SaleUpdateRequest;
import com.fpuna.py.model.response.SaleResponse;
import com.fpuna.py.service.SaleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(ControllerLabels.TAG_SALE)
public class SaleController {

    private static final Logger LOGGER = LogManager.getLogger(SaleController.class);

    private static final String SALE_ID = "/{sale_id}";

    final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSale(@RequestBody SaleRequest request) {
        LOGGER.info("Received request to create sale: {}", request);
        try {
            saleService.createSale(request);
            LOGGER.info("Successfully created sale");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating sale", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSale(@RequestBody SaleUpdateRequest request) {
        LOGGER.info("Received request to update sale: {}", request);
        try {
            saleService.updateSale(request);
            LOGGER.info("Successfully update sale");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updated sale", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = SALE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSale(@PathVariable("sale_id") Integer saleId) {
        LOGGER.info("Received request to delete sale: {}", saleId);
        try {
            saleService.deleteSale(saleId);
            LOGGER.info("Successfully delete sale");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updated sale", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = SALE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSaleById(@PathVariable("sale_id") Integer saleId) {
        LOGGER.info("Received request to get sale: {}", saleId);
        try {
            SaleResponse saleResponse = saleService.getSale(saleId);
            if(Objects.isNull(saleResponse)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            LOGGER.info("Successfully get sale");
            return new ResponseEntity<>(saleResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error get sale", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSales() {
        try {
            List<SaleResponse> saleResponseList = saleService.getSales();
            LOGGER.info("Successfully get sales");
            return new ResponseEntity<>(saleResponseList, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error get sale", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

