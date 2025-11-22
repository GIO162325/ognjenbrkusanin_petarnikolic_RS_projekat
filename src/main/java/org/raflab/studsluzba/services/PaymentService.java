package org.raflab.studsluzba.services;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.model.Uplata;
import org.raflab.studsluzba.model.dtos.PaymentStatusDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    // fiksna školarina i kurs; slobodno promeni po potrebi
    private static final BigDecimal GODISNJA_SKOLARINA_EUR = new BigDecimal("3000.00");
    private static final BigDecimal KURS_EUR_RSD = new BigDecimal("117.00");

    public PaymentStatusDTO izracunajOstatak(List<Uplata> uplate) {
        BigDecimal ukupnoRsd = uplate.stream()
                .map(Uplata::getIznos)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal uplacenoEur = ukupnoRsd
                .divide(KURS_EUR_RSD, 2, RoundingMode.HALF_UP);

        BigDecimal ostatakEur = GODISNJA_SKOLARINA_EUR.subtract(uplacenoEur);
        if (ostatakEur.compareTo(BigDecimal.ZERO) < 0) {
            ostatakEur = BigDecimal.ZERO;
        }

        BigDecimal ostatakRsd = ostatakEur
                .multiply(KURS_EUR_RSD)
                .setScale(2, RoundingMode.HALF_UP);

        PaymentStatusDTO dto = new PaymentStatusDTO();
        dto.setUplacenoEur(uplacenoEur);
        dto.setOstatakEur(ostatakEur);
        dto.setOstatakRsd(ostatakRsd);
        return dto;
    }
}
