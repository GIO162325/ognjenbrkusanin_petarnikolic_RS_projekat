package org.raflab.studsluzba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(
		uniqueConstraints = @UniqueConstraint(
				columnNames = {"broj", "godina", "studProgramOznaka", "aktivan"}
		)
)
public class StudentIndeks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int broj;
	private int godina;
	private String studProgramOznaka;
	private String nacinFinansiranja;
	private boolean aktivan;
	private LocalDate vaziOd;

	@ManyToOne
	private StudentPodaci student;

	@ManyToOne
	private StudijskiProgram studijskiProgram;   // na koji studijski program je upisan

	private Integer ostvarenoEspb;

	// ===== veze na ostale entitete po indeksu =====

	@JsonIgnore
	@OneToMany(mappedBy = "studentIndeks", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Uplata> uplataList = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "studentIndeks", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SlusaPredmet> slusaPredmeti = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "studentIndeks", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PrijavaIspita> prijave = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "studentIndeks", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PolozenPredmet> polozeni = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "studentIndeks", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UpisGodine> upisi = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "studentIndeks", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ObnovaGodine> obnove = new ArrayList<>();
}
