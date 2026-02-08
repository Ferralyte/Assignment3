package edu.aitu.oop3.db.components.billing;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.12");

    public PaymentService() {
    }

    public boolean processPayment(BigDecimal amount) {
        System.out.println("Processing payment of $" + amount);
        return true;
    }


    public BigDecimal calculateTotalWithTax(BigDecimal baseAmount) {
        if (baseAmount == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal taxAmount = baseAmount.multiply(TAX_RATE);
        BigDecimal total = baseAmount.add(taxAmount);

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}