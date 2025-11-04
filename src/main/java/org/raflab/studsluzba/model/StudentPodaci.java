package org.raflab.studsluzba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {"indeksi", "srednjaSkola", "ustanovaPrelaska"})
@ToString(exclude = {"indeksi", "srednjaSkola", "ustanovaPrelaska"})
@Table(indexes = {
		@Index(columnList = "jmbg", unique = true)
})
public class StudentPodaci {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long id;
	 private String ime;	  // not null
	 private String prezime;  // not null
	 private String srednjeIme;   // not null

	 @Column(length = 13, unique = true)
	 private String jmbg;

	 private LocalDate datumRodjenja;  // not null
	 private String mestoRodjenja; 
	 private String mestoPrebivalista;  // not null
	 private String drzavaRodjenja;   
	 private String drzavljanstvo;   // not null
	 private String nacionalnost;   // samoizjasnjavanje, moze bilo sta  
	 private Character pol;    // not null
	 private String adresa;  // not null
	 private String brojTelefonaMobilni;  
	 private String brojTelefonaFiksni;
	 private String email;  // not null
	 private String brojLicneKarte; 
	 private String licnuKartuIzdao;
	 private String mestoStanovanja;
	 private String adresaStanovanja;   // u toku studija

	@ManyToOne
	private SrednjaSkola srednjaSkola;


	private Double uspehSrednja; // prosek
	private Double uspehPrijemni; // bodovi prijemnog


	@ManyToOne
	private VisokoskolskaUstanova ustanovaPrelaska; // ako je prešao


	@JsonIgnore
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentIndeks> indeksi = new ArrayList<>();

}
