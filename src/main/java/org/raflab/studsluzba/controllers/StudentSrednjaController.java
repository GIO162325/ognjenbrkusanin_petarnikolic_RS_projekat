package org.raflab.studsluzba.controllers;

import lombok.RequiredArgsConstructor;
import org.raflab.studsluzba.controllers.response.StudentIndeksResponse;
import org.raflab.studsluzba.model.StudentIndeks;
import org.raflab.studsluzba.repositories.StudentIndeksRepository;
import org.raflab.studsluzba.utils.EntityMappers;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/student")
@RequiredArgsConstructor
public class StudentSrednjaController {

    private final StudentIndeksRepository studentIndeksRepository;
    private final EntityMappers entityMappers;

    // KT1: izbor upisanih studenata koji su završili određenu srednju školu
    @GetMapping("/iz-srednje/{srednjaSkolaId}")
    public List<StudentIndeksResponse> getStudentsFromSrednja(@PathVariable Long srednjaSkolaId) {
        List<StudentIndeks> indeksi =
                studentIndeksRepository.findAktivniBySrednjaSkola(srednjaSkolaId);

        return indeksi.stream()
                .map(entityMappers::fromStudentIndexToResponse)
                .collect(Collectors.toList());
    }
}
