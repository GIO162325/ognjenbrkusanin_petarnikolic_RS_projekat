package org.raflab.studsluzba.controllers;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.controllers.request.ObnovaGodineRequest;
import org.raflab.studsluzba.controllers.request.PrijavaIspitaRequest;
import org.raflab.studsluzba.controllers.request.UpisGodineRequest;
import org.raflab.studsluzba.controllers.request.UplataRequest;
import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.repositories.IspitniRokRepository;
import org.raflab.studsluzba.repositories.PredmetRepository;
import org.raflab.studsluzba.services.StudentAkcijeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentAkcijeController {

    private final StudentAkcijeService service;
    private final PredmetRepository predmetRepository;
    private final IspitniRokRepository ispitniRokRepository;

    @PostMapping("/{indeksId}/upis")
    public Long upis(@PathVariable Long indeksId,
                     @RequestBody @Valid UpisGodineRequest request) {

        UpisGodine upis = new UpisGodine();
        upis.setGodinaUpisa(request.getGodinaUpisa());
        upis.setNapomena(request.getNapomena());

        if (request.getPrenetiPredmetiIds() != null) {
            List<Predmet> preneti = request.getPrenetiPredmetiIds().stream()
                    .map(id -> predmetRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Predmet ne postoji: " + id)))
                    .collect(Collectors.toList());
            upis.setPrenetiPredmeti(preneti);
        }

        return service.upisiGodinu(indeksId, upis).getId();
    }

    @PostMapping("/{indeksId}/obnova")
    public Long obnova(@PathVariable Long indeksId,
                       @RequestBody @Valid ObnovaGodineRequest request) {

        ObnovaGodine obnova = new ObnovaGodine();
        obnova.setGodinaObnove(request.getGodinaObnove());
        obnova.setNapomena(request.getNapomena());

        if (request.getPredmetiZaUpisIds() != null) {
            List<Predmet> predmeti = request.getPredmetiZaUpisIds().stream()
                    .map(id -> predmetRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Predmet ne postoji: " + id)))
                    .collect(Collectors.toList());
            obnova.setPredmetiZaUpis(predmeti);
        }

        return service.obnoviGodinu(indeksId, obnova).getId();
    }

    @PostMapping("/{indeksId}/prijava")
    public Long prijava(@PathVariable Long indeksId,
                        @RequestBody @Valid PrijavaIspitaRequest request) {

        PrijavaIspita prijava = new PrijavaIspita();

        Predmet predmet = predmetRepository.findById(request.getPredmetId())
                .orElseThrow(() -> new IllegalArgumentException("Predmet ne postoji: " + request.getPredmetId()));
        IspitniRok rok = ispitniRokRepository.findById(request.getIspitniRokId())
                .orElseThrow(() -> new IllegalArgumentException("Ispitni rok ne postoji: " + request.getIspitniRokId()));

        prijava.setPredmet(predmet);
        prijava.setIspitniRok(rok);

        return service.prijaviIspit(indeksId, prijava).getId();
    }

    @PostMapping("/{indeksId}/uplata")
    public Long uplata(@PathVariable Long indeksId,
                       @RequestBody @Valid UplataRequest request) {

        Uplata uplata = new Uplata();
        if (request.getIznos() != null) {
            uplata.setIznos(BigDecimal.valueOf(request.getIznos()));
        }

        return service.dodajUplatu(indeksId, uplata).getId();
    }

    @GetMapping("/{indeksId}/ostatak")
    public double ostatak(@PathVariable Long indeksId) {
        return service.preostaliIznosEur(indeksId);
    }

    @GetMapping("/{indeksId}/ostatak-din")
    public double ostatakDin(@PathVariable Long indeksId) {
        return service.preostaliIznosDin(indeksId);
    }

    @GetMapping("/{indeksId}/upisi")
    public List<UpisGodine> upisi(@PathVariable Long indeksId) {
        return service.getUpisi(indeksId);
    }

    @GetMapping("/{indeksId}/obnove")
    public List<ObnovaGodine> obnove(@PathVariable Long indeksId) {
        return service.getObnove(indeksId);
    }

    @GetMapping("/{indeksId}/polozeni")
    public Page<PolozenPredmet> polozeni(@PathVariable Long indeksId, Pageable pageable) {
        return service.getPolozeni(indeksId, pageable);
    }

    @GetMapping("/{indeksId}/nepolozeni")
    public Page<SlusaPredmet> nepolozeni(@PathVariable Long indeksId, Pageable pageable) {
        return service.getNepolozeni(indeksId, pageable);
    }
}
