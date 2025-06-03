package com.fpuna.py.controller;

import com.fpuna.py.model.request.InvoiceRequest;
import com.fpuna.py.model.response.InvoiceResponse;
import com.fpuna.py.service.InvoiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(ControllerLabels.TAG_INVOICE)
public class InvoiceController {

    private static final Logger LOGGER = LogManager.getLogger(InvoiceController.class);

    private static final String INVOICE_ID = "/{invoice_id}";

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest request) {
        LOGGER.info("Received request to create invoice: {}", request);
        try {
            invoiceService.createInvoice(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating invoice", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = INVOICE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInvoice(@PathVariable Integer id) {
        LOGGER.info("Received request to get invoice with ID: {}", id);
        try {
            InvoiceResponse response = invoiceService.getInvoice(id);
            if (Objects.isNull(response)) {
                LOGGER.warn("Invoice with ID {} not found", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error("Error retrieving invoice with ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInvoices() {
        try {
            List<InvoiceResponse> response = invoiceService.getInvoices();
            if (Objects.isNull(response)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error("Error retrieving invoices ", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

