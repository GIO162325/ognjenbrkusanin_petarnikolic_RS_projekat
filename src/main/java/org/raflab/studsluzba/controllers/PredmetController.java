package org.raflab.studsluzba.controllers;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.controllers.request.PredmetCreateRequest;
import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.services.PredmetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/predmet")
public class PredmetController {

	private final PredmetService predmetService;

	@GetMapping(path = "/all")
	public Iterable<Predmet> getAllPredmeti() {
		return predmetService.findAll();
	}

	@GetMapping(path = "/akreditacija/{godina}")
	public List<Predmet> getPredmetiZaGodinuAkreditacije(@PathVariable Integer godina) {
		return predmetService.getPredmetForGodinaAkreditacije(godina);
	}

	@PostMapping(path = "/add")
	public Long addPredmet(@RequestBody PredmetCreateRequest request) {
		return predmetService.kreirajPredmet(request);
	}
}
