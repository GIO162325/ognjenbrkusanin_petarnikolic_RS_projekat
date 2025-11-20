package org.raflab.studsluzba.repositories;

import java.util.List;

import org.raflab.studsluzba.model.SlusaPredmet;
import org.raflab.studsluzba.model.StudentIndeks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SlusaPredmetRepository extends JpaRepository<SlusaPredmet, Long> {

	@Query("select sp from SlusaPredmet sp where sp.studentIndeks.id = ?1")
	List<SlusaPredmet> getSlusaPredmetForIndeksAktivnaGodina(Long indeksId);

	@Query("select sp.studentIndeks from SlusaPredmet sp where sp.drziPredmet.predmet.id = :idPredmeta "
			+ "and sp.drziPredmet.nastavnik.id = :idNastavnika")
	List<StudentIndeks> getStudentiSlusaPredmetAktivnaGodina(Long idPredmeta, Long idNastavnika);

	@Query("select sp.studentIndeks from SlusaPredmet sp where sp.drziPredmet.id = :idDrziPredmet")
	List<StudentIndeks> getStudentiSlusaPredmetZaDrziPredmet(Long idDrziPredmet);

	Page<SlusaPredmet> findByStudentIndeksId(Long studentIndeksId, Pageable pageable);

	@Query("select sp from SlusaPredmet sp " +
			"where sp.studentIndeks.id = ?1 " +
			"and not exists (" +
			" select pp from PolozenPredmet pp " +
			" where pp.studentIndeks.id = sp.studentIndeks.id " +
			" and pp.predmet.id = sp.drziPredmet.predmet.id" +
			")")
	Page<SlusaPredmet> findNepolozeniForIndeks(Long indeksId, Pageable pageable);
}
