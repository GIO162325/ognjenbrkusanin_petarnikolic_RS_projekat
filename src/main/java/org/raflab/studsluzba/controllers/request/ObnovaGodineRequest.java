package org.raflab.studsluzba.controllers.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ObnovaGodineRequest {

    private Long studentIndeksId;
    private Integer godinaObnove;
    private LocalDate datumObnove;         // null -> today
    private String napomena;
    private List<Long> predmetiZaUpisIds;  // id-jevi predmeta koje će ponovo slušati
}
