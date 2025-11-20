package org.raflab.studsluzba.controllers.request;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ObnovaGodineRequest {
    @NotNull
    private Integer godinaObnove;
    private String napomena;
    private List<Long> predmetiZaUpisIds;
}
