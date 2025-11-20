package org.raflab.studsluzba.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class UpisGodine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // npr. 1, 2, 3, 4
    private Integer godinaUpisa;

    private LocalDate datumUpisa;

    private String napomena;

    @ManyToMany
    @JoinTable(
            name = "upisgodine_predmet",
            joinColumns = @JoinColumn(name = "upis_id"),
            inverseJoinColumns = @JoinColumn(name = "predmet_id")
    )
    private List<Predmet> prenetiPredmeti;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_indeks_id")
    private StudentIndeks studentIndeks;
}
