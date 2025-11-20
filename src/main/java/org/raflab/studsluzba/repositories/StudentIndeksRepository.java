package org.raflab.studsluzba.repositories;

import java.util.List;

import org.raflab.studsluzba.model.StudentIndeks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentIndeksRepository extends JpaRepository<StudentIndeks, Long> {

	@Query("select indeks from StudentIndeks indeks " +
			"where indeks.studProgramOznaka like ?1 and indeks.godina = ?2 and indeks.broj = ?3")
	StudentIndeks findStudentIndeks(String studProgramOznaka, int godina, int broj);

	@Query("select indeks from StudentIndeks indeks where " +
			"(:ime is null or lower(indeks.student.ime) like :ime) and " +
			"(:prezime is null or lower(indeks.student.prezime) like :prezime) and " +
			"(:studProgramOznaka is null or lower(indeks.studProgramOznaka) like :studProgramOznaka) and " +
			"(:godina is null or indeks.godina = :godina) and " +
			"(:broj is null or indeks.broj = :broj)")
	Page<StudentIndeks> findStudentIndeks(@Param("ime") String ime,
										  @Param("prezime") String prezime,
										  @Param("studProgramOznaka") String studProgramOznaka,
										  @Param("godina") Integer godina,
										  @Param("broj") Integer broj,
										  Pageable pageable);

	@Query("select indeks.broj from StudentIndeks indeks " +
			"where indeks.godina = :godina and indeks.studProgramOznaka = :studProgramOznaka " +
			"order by indeks.broj asc")
	List<Integer> findBrojeviByGodinaAndStudProgramOznaka(@Param("godina") Integer godina,
														  @Param("studProgramOznaka") String studProgramOznaka);

	@Query("select indeks from StudentIndeks indeks " +
			"where indeks.aktivan = true and indeks.student.id = :studPodaciId")
	List<StudentIndeks> findAktivanStudentIndeksiByStudentPodaciId(@Param("studPodaciId") Long studPodaciId);

	// NOVO: aktivni indeksi studenata koji su završili određenu srednju školu
	@Query("select indeks from StudentIndeks indeks " +
			"where indeks.aktivan = true and indeks.student.srednjaSkola.id = :srednjaSkolaId")
	List<StudentIndeks> findAktivniBySrednjaSkola(@Param("srednjaSkolaId") Long srednjaSkolaId);
}
