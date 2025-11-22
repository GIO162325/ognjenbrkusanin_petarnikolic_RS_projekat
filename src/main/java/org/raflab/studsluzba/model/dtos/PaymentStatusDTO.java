package org.raflab.studsluzba.model.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentStatusDTO {

    private BigDecimal uplacenoEur;
    private BigDecimal ostatakEur;
    private BigDecimal ostatakRsd;
}
