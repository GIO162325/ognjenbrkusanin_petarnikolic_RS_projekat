package org.raflab.studsluzba.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObnovaGodineRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(1)
    @Max(8)
    private Integer godinaObnove;

    @Size(max = 1000)
    private String napomena;

    @NotNull
    @NotEmpty
    private List<@NotNull Long> predmetiZaUpisIds;
}
