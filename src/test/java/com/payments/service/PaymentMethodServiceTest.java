package com.payments.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * KAN-1: Add Payment endpoint to Payments API
 */
class PaymentMethodServiceTest {

    private final PaymentMethodService service = new PaymentMethodService();

    @Test
    void cardPayment_returnsCharged() {
        PaymentRequest req = PaymentRequest.builder()
            .customerId("cust-1").amountCents(1000L).method(PaymentMethod.CARD).build();
        assertEquals(PaymentStatus.CHARGED, service.processPayment(req).getStatus());
    }

    @Test
    void bankTransfer_withToken_returnsPendingSettlement() {
        PaymentRequest req = PaymentRequest.builder()
            .customerId("cust-1").amountCents(1000L)
            .method(PaymentMethod.BANK_TRANSFER).bankAccountToken("tok_test_123").build();
        assertEquals(PaymentStatus.PENDING_SETTLEMENT, service.processPayment(req).getStatus());
    }

    @Test
    void bankTransfer_withoutToken_throws() {
        PaymentRequest req = PaymentRequest.builder()
            .customerId("cust-1").amountCents(1000L).method(PaymentMethod.BANK_TRANSFER).build();
        assertThrows(IllegalArgumentException.class, () -> service.processPayment(req));
    }
}