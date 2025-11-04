package org.raflab.studsluzba.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {"predmetNaGodini"})
@ToString(exclude = {"predmetNaGodini"})
public class PredispitnaObaveza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    private Predmet predmet;


    private String vrsta; // TEST, KOLOKVIJUM… (može i enum)
    private Integer maxPoena;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}