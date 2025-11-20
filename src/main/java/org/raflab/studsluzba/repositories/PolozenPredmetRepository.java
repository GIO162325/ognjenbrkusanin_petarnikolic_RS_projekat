package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.PolozenPredmet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolozenPredmetRepository extends JpaRepository<PolozenPredmet, Long> {

    Page<PolozenPredmet> findByStudentIndeksId(Long studentIndeksId, Pageable pageable);
}
