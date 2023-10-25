package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/professor")
@AllArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @PostMapping("/criar")
    public ResponseEntity<Object> criarProfessor(@RequestBody Professor professor){
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.criarProfessor(professor));
    }
}
