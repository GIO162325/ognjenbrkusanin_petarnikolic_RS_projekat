package org.raflab.studsluzba.services;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.model.*;
import org.raflab.studsluzba.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentAkcijeService {

    private static final double DEFAULT_KURS_EUR = 117.0;
    private static final double GODISNJA_SKOLARINA_EUR = 3000.0;
    private static final String EUR_RATE_URL =
            "https://kurs.resenje.org/api/v1/currencies/eur/rates/today";

    private final UpisGodineRepository upisRepo;
    private final ObnovaGodineRepository obnovaRepo;
    private final PrijavaIspitaRepository prijavaRepo;
    private final UplataRepository uplataRepo;
    private final StudentIndeksRepository studentIndeksRepo;
    private final PolozenPredmetRepository polozenPredmetRepo;
    private final SlusaPredmetRepository slusaPredmetRepo;


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

        int ukupnoEspb = 0;
        if (obnova.getPredmetiZaUpis() != null) {
            for (Predmet p : obnova.getPredmetiZaUpis()) {
                if (p != null && p.getEspb() != null) {
                    ukupnoEspb += p.getEspb();
                }
            }
        }

        if (ukupnoEspb > 60) {
            throw new IllegalArgumentException(
                    "Ukupan zbir ESPB za obnovu ne sme preći 60 (trenutno: " + ukupnoEspb + ")"
            );
        }

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

        double kurs = getTrenutniKurs();
        uplata.setSrednjiKurs(BigDecimal.valueOf(kurs));

        return uplataRepo.save(uplata);
    }


    public double preostaliIznosEur(Long indeksId) {
        StudentIndeks indeks = studentIndeksRepo.findById(indeksId)
                .orElseThrow(() -> new RuntimeException("Student indeks ne postoji"));

        double ukupnoEur = 0.0;

        for (Uplata u : indeks.getUplataList()) {
            if (u.getIznos() == null) {
                continue;
            }
            double kurs = DEFAULT_KURS_EUR;
            if (u.getSrednjiKurs() != null) {
                kurs = u.getSrednjiKurs().doubleValue();
            }

            if (kurs <= 0) {
                kurs = DEFAULT_KURS_EUR;
            }

            // iznos je u dinarima, deli se srednjim kursom da se dobije iznos u evrima
            ukupnoEur += u.getIznos().doubleValue() / kurs;
        }

        return GODISNJA_SKOLARINA_EUR - ukupnoEur;
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

    public Page<PolozenPredmet> getPolozeni(Long indeksId, Pageable pageable) {
        return polozenPredmetRepo.findByStudentIndeksId(indeksId, pageable);
    }

    public Page<SlusaPredmet> getNepolozeni(Long indeksId, Pageable pageable) {
        return slusaPredmetRepo.findNepolozeniForIndeks(indeksId, pageable);
    }


    /**
     * Pokušava da pročita kurs sa kurs.resenje.org, u suprotnom vraća DEFAULT_KURS_EUR.
     */
    private double getTrenutniKurs() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            @SuppressWarnings("unchecked")
            Map<String, Object> json =
                    restTemplate.getForObject(EUR_RATE_URL, Map.class);

            if (json == null) {
                return DEFAULT_KURS_EUR;
            }

            // varijanta 1: {"exchange_middle": 117.1234, ...}
            Object middle = json.get("exchange_middle");
            if (middle instanceof Number) {
                return ((Number) middle).doubleValue();
            }

            // ako format API-ja nije očekivan – fallback
            return DEFAULT_KURS_EUR;
        } catch (Exception ex) {
            // u slučaju bilo kakve greške se ne ruši aplikacija već koristi default
            return DEFAULT_KURS_EUR;
        }
    }
}
