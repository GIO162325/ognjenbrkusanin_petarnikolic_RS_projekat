package org.raflab.studsluzba.controllers.request;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UplataRequest {
    @NotNull
    private Double iznos;
}
