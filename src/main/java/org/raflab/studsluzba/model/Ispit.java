package org.raflab.studsluzba.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Ispit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate datumPocetka;
    private LocalDate datumZavrsetka;
    @ManyToOne
    @JoinColumn(name = "predmet_id")
    private Predmet predmet;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
