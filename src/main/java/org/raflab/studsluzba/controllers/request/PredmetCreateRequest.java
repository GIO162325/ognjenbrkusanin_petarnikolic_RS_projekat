package org.raflab.studsluzba.controllers.request;

import lombok.Data;

@Data
public class PredmetCreateRequest {

    private String sifra;
    private String naziv;
    private String opis;
    private Integer espb;
    private boolean obavezan;

    private Long studijskiProgramId;
}
