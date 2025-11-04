package org.raflab.studsluzba.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {})
@ToString
@Table(indexes = {@Index(columnList = "pocetnaGodina", unique = true)})
public class SkolskaGodina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer pocetnaGodina; // 2024 -> 2024/25
    private boolean aktivna = false;
}
