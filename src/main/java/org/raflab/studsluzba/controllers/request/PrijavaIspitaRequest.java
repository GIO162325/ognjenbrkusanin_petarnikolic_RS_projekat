package org.raflab.studsluzba.controllers.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrijavaIspitaRequest {

    private Long studentIndeksId;
    private Long predmetId;
    private Long ispitniRokId;
    private LocalDate datumPrijave;    // null -> today
}
