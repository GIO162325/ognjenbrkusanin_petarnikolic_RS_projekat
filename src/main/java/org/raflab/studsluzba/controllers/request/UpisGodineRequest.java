package org.raflab.studsluzba.controllers.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpisGodineRequest {

    private Long studentIndeksId;
    private Integer godinaUpisa;
    private LocalDate datumUpisa;          // možeš da pošalješ ili ostaviš null -> today
    private String napomena;
    private List<Long> prenetiPredmetiIds; // id-jevi predmeta koji se prenose
}
