package org.raflab.studsluzba.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UplataRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private Double iznos;
}
