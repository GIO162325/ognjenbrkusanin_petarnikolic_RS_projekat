package org.raflab.studsluzba.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(exclude = {"studentIndex", "predmet", "priznatoSaUstanove", "ispit"})
@ToString(exclude = {"studentIndex", "predmet", "priznatoSaUstanove", "ispit"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"studentindeks_id", "predmet_id"}))
public class PolozenPredmet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    private StudentIndeks studentIndeks;


    @ManyToOne(optional = false)
    private Predmet predmet;


    private String nacin; // "ISPIT" ili "PRIZNAT"


    private Integer ocena; // 6–10
    private LocalDate datum;


    @ManyToOne
    private VisokoskolskaUstanova priznatoSaUstanove; // ako je priznato


    @ManyToOne
    private Ispit ispit; // ako je položeno putem ispita
}
