package org.raflab.studsluzba.controllers.request;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpisGodineRequest {
    @NotNull
    private Integer godinaUpisa;
    private String napomena;
    private List<Long> prenetiPredmetiIds;
}
