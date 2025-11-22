package org.raflab.studsluzba.controllers.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UplataRequest {

    private Long studentIndeksId;
    private BigDecimal iznos;      // u dinarima
    private LocalDate datum;       // null -> today
    private String svrha;
    private String pozivNaBroj;
}
