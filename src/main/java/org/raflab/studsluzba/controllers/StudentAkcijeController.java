package org.raflab.studsluzba.controllers;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.model.ObnovaGodine;
import org.raflab.studsluzba.model.PrijavaIspita;
import org.raflab.studsluzba.model.UpisGodine;
import org.raflab.studsluzba.model.Uplata;
import org.raflab.studsluzba.services.StudentAkcijeService;
import org.springframework.web.bind.annotation.*;

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
}
