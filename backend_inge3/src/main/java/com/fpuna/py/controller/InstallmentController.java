package com.fpuna.py.controller;

import com.fpuna.py.model.response.InstallmentResponse;
import com.fpuna.py.service.InstallmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ControllerLabels.TAG_INSTALLMENT)
public class InstallmentController {

    private static final Logger LOGGER = LogManager.getLogger(InstallmentController.class);

    private static final String FIND_INSTALLMENT_BY_INVOICE = "/invoice/{invoiceId}";

    @Autowired
    private InstallmentService installmentService;

    @GetMapping(value = FIND_INSTALLMENT_BY_INVOICE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstallmentResponse>> getInstallmentsByInvoice(@PathVariable Integer invoiceId) {
        LOGGER.info("Received request to get installments for invoiceId: {}", invoiceId);
        try {
            List<InstallmentResponse> installments = installmentService.getInstallmentsByInvoice(invoiceId);
            LOGGER.info("Successfully retrieved {} installments for invoiceId: {}", installments.size(), invoiceId);
            return ResponseEntity.ok(installments);
        } catch (Exception e) {
            LOGGER.error("Error retrieving installments for invoiceId: {}", invoiceId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

