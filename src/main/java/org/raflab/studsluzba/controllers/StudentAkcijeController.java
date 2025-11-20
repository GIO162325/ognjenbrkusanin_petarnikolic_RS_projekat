package org.raflab.studsluzba.controllers;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.services.StudentAkcijeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentAkcijeController {

    private final StudentAkcijeService service;

    @PostMapping("/{indeksId}/upis")
    public Long upis(@PathVariable Long indeksId, @RequestBody UpisGodine upis) {
        return service.upisiGodinu(indeksId, upis).getId();
    }

    @PostMapping("/{indeksId}/obnova")
    public Long obnova(@PathVariable Long indeksId, @RequestBody ObnovaGodine obnova) {
        return service.obnoviGodinu(indeksId, obnova).getId();
    }

    @PostMapping("/{indeksId}/prijava")
    public Long prijava(@PathVariable Long indeksId, @RequestBody PrijavaIspita prijava) {
        return service.prijaviIspit(indeksId, prijava).getId();
    }

    @PostMapping("/{indeksId}/uplata")
    public Long uplata(@PathVariable Long indeksId, @RequestBody Uplata uplata) {
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
