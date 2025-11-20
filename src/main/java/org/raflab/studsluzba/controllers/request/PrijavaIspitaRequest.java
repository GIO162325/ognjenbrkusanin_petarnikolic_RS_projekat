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
public class PrijavaIspitaRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private Long predmetId;

    @NotNull
    @Positive
    private Long ispitniRokId;
}
