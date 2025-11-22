package org.raflab.studsluzba.services;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.controllers.request.ObnovaGodineRequest;
import org.raflab.studsluzba.controllers.request.PrijavaIspitaRequest;
import org.raflab.studsluzba.controllers.request.UpisGodineRequest;
import org.raflab.studsluzba.controllers.request.UplataRequest;
import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.model.dtos.PaymentStatusDTO;
import org.raflab.studsluzba.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAkcijeService {

    private final UpisGodineRepository upisGodineRepository;
    private final ObnovaGodineRepository obnovaGodineRepository;
    private final PrijavaIspitaRepository prijavaIspitaRepository;
    private final UplataRepository uplataRepository;
    private final StudentIndeksRepository studentIndeksRepository;
    private final PredmetRepository predmetRepository;
    private final IspitniRokRepository ispitniRokRepository;
    private final PaymentService paymentService;

    // -------------------- UPIS GODINE --------------------

    @Transactional
    public Long upisiGodinu(UpisGodineRequest request) {
        StudentIndeks indeks = studentIndeksRepository.findById(request.getStudentIndeksId())
                .orElseThrow(() -> new IllegalArgumentException("StudentIndeks not found"));

        UpisGodine upis = new UpisGodine();
        upis.setStudentIndeks(indeks);
        upis.setGodinaUpisa(request.getGodinaUpisa());
        upis.setDatumUpisa(request.getDatumUpisa() != null ? request.getDatumUpisa() : LocalDate.now());
        upis.setNapomena(request.getNapomena());

        if (request.getPrenetiPredmetiIds() != null && !request.getPrenetiPredmetiIds().isEmpty()) {
            List<Predmet> preneti = new ArrayList<>();
            predmetRepository.findAllById(request.getPrenetiPredmetiIds())
                    .forEach(praneti -> preneti.add(praneti));
            upis.setPrenetiPredmeti(preneti);
        } else {
            upis.setPrenetiPredmeti(Collections.emptyList());
        }

        return upisGodineRepository.save(upis).getId();
    }

    // -------------------- OBNOVA GODINE --------------------

    @Transactional
    public Long obnovaGodine(ObnovaGodineRequest request) {
        StudentIndeks indeks = studentIndeksRepository.findById(request.getStudentIndeksId())
                .orElseThrow(() -> new IllegalArgumentException("StudentIndeks not found"));

        ObnovaGodine obnova = new ObnovaGodine();
        obnova.setStudentIndeks(indeks);
        obnova.setGodinaObnove(request.getGodinaObnove());
        obnova.setDatumObnove(request.getDatumObnove() != null ? request.getDatumObnove() : LocalDate.now());
        obnova.setNapomena(request.getNapomena());

        if (request.getPredmetiZaUpisIds() != null && !request.getPredmetiZaUpisIds().isEmpty()) {
            List<Predmet> predmeti = new ArrayList<>();
            predmetRepository.findAllById(request.getPredmetiZaUpisIds())
                    .forEach(predmeti::add);
            obnova.setPredmetiZaUpis(predmeti);
        } else {
            obnova.setPredmetiZaUpis(Collections.emptyList());
        }


        return obnovaGodineRepository.save(obnova).getId();
    }

    // -------------------- PRIJAVA ISPITA --------------------

    @Transactional
    public Long prijavaIspita(PrijavaIspitaRequest request) {
        StudentIndeks indeks = studentIndeksRepository.findById(request.getStudentIndeksId())
                .orElseThrow(() -> new IllegalArgumentException("StudentIndeks not found"));

        Predmet predmet = predmetRepository.findById(request.getPredmetId())
                .orElseThrow(() -> new IllegalArgumentException("Predmet not found"));

        IspitniRok rok = ispitniRokRepository.findById(request.getIspitniRokId())
                .orElseThrow(() -> new IllegalArgumentException("Ispitni rok not found"));

        PrijavaIspita prijava = new PrijavaIspita();
        prijava.setStudentIndeks(indeks);
        prijava.setPredmet(predmet);
        prijava.setIspitniRok(rok);
        prijava.setDatumPrijave(request.getDatumPrijave() != null ? request.getDatumPrijave() : LocalDate.now());

        return prijavaIspitaRepository.save(prijava).getId();
    }

    // -------------------- UPLATE / OSTATAK --------------------

    @Transactional
    public Long dodajUplatu(UplataRequest request) {
        StudentIndeks indeks = studentIndeksRepository.findById(request.getStudentIndeksId())
                .orElseThrow(() -> new IllegalArgumentException("StudentIndeks not found"));

        Uplata uplata = new Uplata();
        uplata.setStudentIndeks(indeks);
        uplata.setIznos(request.getIznos());
        uplata.setDatum(request.getDatum() != null ? request.getDatum() : LocalDate.now());
        uplata.setSvrha(request.getSvrha());
        uplata.setPozivNaBroj(request.getPozivNaBroj());

        return uplataRepository.save(uplata).getId();
    }

    public PaymentStatusDTO izracunajOstatak(Long studentIndeksId) {
        List<Uplata> uplate = uplataRepository.findByStudentIndeksId(studentIndeksId);
        return paymentService.izracunajOstatak(uplate);
    }


    public List<UpisGodine> getUpisiForStudent(Long studentIndeksId) {
        return upisGodineRepository.findByStudentIndeksIdOrderByGodinaUpisaAsc(studentIndeksId);
    }

    public List<ObnovaGodine> getObnoveForStudent(Long studentIndeksId) {
        return obnovaGodineRepository.findByStudentIndeksIdOrderByGodinaObnoveAsc(studentIndeksId);
    }

    public List<PrijavaIspita> getPrijaveForStudent(Long studentIndeksId) {
        return prijavaIspitaRepository.findByStudentIndeksId(studentIndeksId);
    }
}
