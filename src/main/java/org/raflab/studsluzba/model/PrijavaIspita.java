package org.raflab.studsluzba.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class PrijavaIspita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datumPrijave;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_indeks_id")
    private StudentIndeks studentIndeks;

    @ManyToOne(optional = false)
    @JoinColumn(name = "predmet_id")
    private Predmet predmet;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ispitni_rok_id")
    private IspitniRok ispitniRok;
}
