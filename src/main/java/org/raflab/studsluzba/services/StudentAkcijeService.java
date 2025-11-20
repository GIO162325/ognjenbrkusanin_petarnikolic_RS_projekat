package org.raflab.studsluzba.services;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAkcijeService {

    private static final double DEFAULT_KURS_EUR = 117.0;

    private final UpisGodineRepository upisRepo;
    private final ObnovaGodineRepository obnovaRepo;
    private final PrijavaIspitaRepository prijavaRepo;
    private final UplataRepository uplataRepo;
    private final StudentIndeksRepository studentIndeksRepo;

    public UpisGodine upisiGodinu(Long indeksId, UpisGodine upis) {
        StudentIndeks indeks = studentIndeksRepo.findById(indeksId)
                .orElseThrow(() -> new RuntimeException("Student indeks ne postoji"));

        upis.setStudentIndeks(indeks);
        upis.setDatumUpisa(LocalDate.now());

        return upisRepo.save(upis);
    }

    public ObnovaGodine obnoviGodinu(Long indeksId, ObnovaGodine obnova) {
        StudentIndeks indeks = studentIndeksRepo.findById(indeksId)
                .orElseThrow(() -> new RuntimeException("Student indeks ne postoji"));

        obnova.setStudentIndeks(indeks);
        obnova.setDatumObnove(LocalDate.now());

        return obnovaRepo.save(obnova);
    }

    public PrijavaIspita prijaviIspit(Long indeksId, PrijavaIspita prijava) {
        StudentIndeks indeks = studentIndeksRepo.findById(indeksId)
                .orElseThrow(() -> new RuntimeException("Student indeks ne postoji"));

        prijava.setStudentIndeks(indeks);
        prijava.setDatumPrijave(LocalDate.now());

        return prijavaRepo.save(prijava);
    }

    public Uplata dodajUplatu(Long indeksId, Uplata uplata) {
        StudentIndeks indeks = studentIndeksRepo.findById(indeksId)
                .orElseThrow(() -> new RuntimeException("Student indeks ne postoji"));

        uplata.setStudentIndeks(indeks);
        uplata.setDatum(LocalDate.now());

        return uplataRepo.save(uplata);
    }

    public double preostaliIznosEur(Long indeksId) {
        StudentIndeks indeks = studentIndeksRepo.findById(indeksId)
                .orElseThrow(() -> new RuntimeException("Student indeks ne postoji"));

        double ukupnoDin = 0.0;
        for (Uplata u : indeks.getUplataList()) {
            if (u.getIznos() != null) {
                ukupnoDin += u.getIznos().doubleValue();
            }
        }

        double kurs = getTrenutniKurs();
        double ukupnoEur = ukupnoDin / kurs;
        return 3000.0 - ukupnoEur;
    }

    public double preostaliIznosDin(Long indeksId) {
        double preostaloEur = preostaliIznosEur(indeksId);
        return preostaloEur * getTrenutniKurs();
    }

    public List<UpisGodine> getUpisi(Long indeksId) {
        return upisRepo.findByStudentIndeksId(indeksId);
    }

    public List<ObnovaGodine> getObnove(Long indeksId) {
        return obnovaRepo.findByStudentIndeksId(indeksId);
    }

    private double getTrenutniKurs() {
        return DEFAULT_KURS_EUR;
    }
}
