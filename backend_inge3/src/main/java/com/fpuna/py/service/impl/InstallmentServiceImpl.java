package com.fpuna.py.service.impl;

import com.fpuna.py.entity.Installment;
import com.fpuna.py.model.response.InstallmentResponse;
import com.fpuna.py.repository.InstallmentRepository;
import com.fpuna.py.service.InstallmentService;
import com.fpuna.py.util.MethodUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    final InstallmentRepository installmentRepository;

    public InstallmentServiceImpl(InstallmentRepository installmentRepository) {
        this.installmentRepository = installmentRepository;
    }

    @Override
    public List<InstallmentResponse> getInstallmentsByInvoice(Integer invoiceId) {
        List<Installment> installments = installmentRepository.findByInvoiceId(invoiceId);
        return installments.stream()
                .map(inst -> new InstallmentResponse(
                        inst.getId(),
                        inst.getInvoice().getId(),
                        inst.getInstallmentNumber(),
                        inst.getAmount(),
                        MethodUtils.convertLocalDateToString(inst.getDueDate()),
                        inst.getPaid(),
                        MethodUtils.convertLocalDateToString(inst.getPaymentDate())))
                .toList();
    }
}
