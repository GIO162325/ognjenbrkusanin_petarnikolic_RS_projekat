package org.raflab.studsluzba.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class ObnovaGodine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // godina koja se obnavlja (1,2,3,4)
    private Integer godinaObnove;

    private LocalDate datumObnove;

    private String napomena;

    @ManyToMany
    @JoinTable(
            name = "obnova_predmet",
            joinColumns = @JoinColumn(name = "obnova_id"),
            inverseJoinColumns = @JoinColumn(name = "predmet_id")
    )
    private List<Predmet> predmetiZaUpis;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_indeks_id")
    private StudentIndeks studentIndeks;
}
