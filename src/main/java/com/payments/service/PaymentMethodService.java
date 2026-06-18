package com.payments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * KAN-1: Add Payment endpoint to Payments API
 * PCI note: account/card numbers are never logged or stored in plain text.
 */
@Service
public class PaymentMethodService {

    @Transactional
    public PaymentResult processPayment(PaymentRequest request) {
        if (request.getMethod() == null) {
            throw new IllegalArgumentException("Payment method is required");
        }
        return switch (request.getMethod()) {
            case CARD -> processCard(request);
            case BANK_TRANSFER -> processBankTransfer(request);
        };
    }

    private PaymentResult processCard(PaymentRequest request) {
        return PaymentResult.builder()
            .customerId(request.getCustomerId())
            .amountCents(request.getAmountCents())
            .method(PaymentMethod.CARD)
            .status(PaymentStatus.CHARGED)
            .build();
    }

    private PaymentResult processBankTransfer(PaymentRequest request) {
        if (request.getBankAccountToken() == null) {
            throw new IllegalArgumentException("bankAccountToken is required for BANK_TRANSFER");
        }
        return PaymentResult.builder()
            .customerId(request.getCustomerId())
            .amountCents(request.getAmountCents())
            .method(PaymentMethod.BANK_TRANSFER)
            .status(PaymentStatus.PENDING_SETTLEMENT)
            .build();
    }
}