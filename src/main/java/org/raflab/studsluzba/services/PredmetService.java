package org.raflab.studsluzba.services;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.controllers.request.PredmetCreateRequest;
import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.model.StudijskiProgram;
import org.raflab.studsluzba.repositories.PredmetRepository;
import org.raflab.studsluzba.repositories.StudijskiProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PredmetService {

    private final PredmetRepository predmetRepository;
    private final StudijskiProgramRepository studijskiProgramRepository;

    public Iterable<Predmet> findAll() {
        return predmetRepository.findAll();
    }

    public List<Predmet> getPredmetForGodinaAkreditacije(Integer godinaAkreditacije) {
        return predmetRepository.getPredmetForGodinaAkreditacije(godinaAkreditacije);
    }

    public Long kreirajPredmet(PredmetCreateRequest request) {
        StudijskiProgram sp = studijskiProgramRepository
                .findById(request.getStudijskiProgramId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Studijski program ne postoji, id=" + request.getStudijskiProgramId()));

        Predmet predmet = new Predmet();
        predmet.setSifra(request.getSifra());
        predmet.setNaziv(request.getNaziv());
        predmet.setOpis(request.getOpis());
        predmet.setEspb(request.getEspb());
        predmet.setObavezan(request.isObavezan());
        predmet.setStudProgram(sp);

        return predmetRepository.save(predmet).getId();
    }
}
