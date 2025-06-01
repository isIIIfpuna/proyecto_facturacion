package com.fpuna.py.service.impl;

import com.fpuna.py.entity.Installment;
import com.fpuna.py.model.response.InstallmentResponse;
import com.fpuna.py.repository.InstallmentRepository;
import com.fpuna.py.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    @Autowired
    InstallmentRepository installmentRepository;

    @Override
    public List<InstallmentResponse> getInstallmentsByInvoice(Integer invoiceId) {
        List<Installment> installments = installmentRepository.findByInvoiceId(invoiceId);
        return installments.stream()
                .map(inst -> new InstallmentResponse(
                        inst.getId(),
                        inst.getInvoice().getId(),
                        inst.getInstallmentNumber(),
                        inst.getAmount(),
                        inst.getDueDate(),
                        inst.getPaid()))
                .toList();
    }
}
