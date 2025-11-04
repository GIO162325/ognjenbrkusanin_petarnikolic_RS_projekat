package org.raflab.studsluzba.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(exclude = {"studentIndeks"})
@ToString(exclude = {"studentIndeks"})
public class Uplata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    private StudentIndeks studentIndeks; // uplata po indeksu/programu


    @Column(precision = 12, scale = 2)
    private BigDecimal iznos;


    private LocalDate datum;
    private String svrha;
    private String pozivNaBroj;
}
