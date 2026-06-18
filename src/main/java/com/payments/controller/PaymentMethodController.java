package com.payments.controller;

import com.payments.service.PaymentMethodService;
import com.payments.service.PaymentRequest;
import com.payments.service.PaymentResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * KAN-1: Add Payment endpoint to Payments API
 */
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping
    public ResponseEntity<PaymentResult> processPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentMethodService.processPayment(request));
    }
}