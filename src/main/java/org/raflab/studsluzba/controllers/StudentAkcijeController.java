package org.raflab.studsluzba.controllers;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.controllers.request.ObnovaGodineRequest;
import org.raflab.studsluzba.controllers.request.PrijavaIspitaRequest;
import org.raflab.studsluzba.controllers.request.UpisGodineRequest;
import org.raflab.studsluzba.controllers.request.UplataRequest;
import org.raflab.studsluzba.model.ObnovaGodine;
import org.raflab.studsluzba.model.PrijavaIspita;
import org.raflab.studsluzba.model.UpisGodine;
import org.raflab.studsluzba.model.dtos.PaymentStatusDTO;
import org.raflab.studsluzba.services.StudentAkcijeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/student/akcije")
public class StudentAkcijeController {

    private final StudentAkcijeService studentAkcijeService;

    @PostMapping("/upis-godine")
    public Long upisiGodinu(@RequestBody UpisGodineRequest request) {
        return studentAkcijeService.upisiGodinu(request);
    }

    @GetMapping("/upis/{studentIndeksId}")
    public List<UpisGodine> getUpisiZaStudenta(@PathVariable Long studentIndeksId) {
        return studentAkcijeService.getUpisiForStudent(studentIndeksId);
    }

    @PostMapping("/obnova-godine")
    public Long obnovaGodine(@RequestBody ObnovaGodineRequest request) {
        return studentAkcijeService.obnovaGodine(request);
    }

    @GetMapping("/obnove/{studentIndeksId}")
    public List<ObnovaGodine> getObnoveZaStudenta(@PathVariable Long studentIndeksId) {
        return studentAkcijeService.getObnoveForStudent(studentIndeksId);
    }

    @PostMapping("/prijava-ispita")
    public Long prijavaIspita(@RequestBody PrijavaIspitaRequest request) {
        return studentAkcijeService.prijavaIspita(request);
    }

    @GetMapping("/prijave/{studentIndeksId}")
    public List<PrijavaIspita> getPrijaveZaStudenta(@PathVariable Long studentIndeksId) {
        return studentAkcijeService.getPrijaveForStudent(studentIndeksId);
    }

    @PostMapping("/uplata")
    public Long dodajUplatu(@RequestBody UplataRequest request) {
        return studentAkcijeService.dodajUplatu(request);
    }

    @GetMapping("/stanje/{studentIndeksId}")
    public PaymentStatusDTO getStanje(@PathVariable Long studentIndeksId) {
        return studentAkcijeService.izracunajOstatak(studentIndeksId);
    }
}
