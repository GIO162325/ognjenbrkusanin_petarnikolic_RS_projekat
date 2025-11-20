package org.raflab.studsluzba.repositories;

import org.raflab.studsluzba.model.Uplata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UplataRepository extends JpaRepository<Uplata, Long> {
}
