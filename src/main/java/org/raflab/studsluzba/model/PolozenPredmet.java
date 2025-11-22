package org.raflab.studsluzba.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(exclude = {"studentIndeks", "predmet", "priznatoSaUstanove", "ispit"})
@ToString(exclude = {"studentIndeks", "predmet", "priznatoSaUstanove", "ispit"})
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_indeks_id", "predmet_id"})
)
public class PolozenPredmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private StudentIndeks studentIndeks;

    @ManyToOne(optional = false)
    private Predmet predmet;

    /**
     * ISPIT ili PRIZNAT (sa druge ustanove).
     */
    private String nacin;

    private Integer ocena; // 6–10
    private LocalDate datum;

    @ManyToOne
    private VisokoskolskaUstanova priznatoSaUstanove;

    @ManyToOne
    private Ispit ispit;
}
