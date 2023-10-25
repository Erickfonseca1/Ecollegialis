package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ColegiadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/colegiado")
@AllArgsConstructor
public class ColegiadoController {

    private final ColegiadoService colegiadoService;

    @PostMapping("/criar")
    public ResponseEntity<Object> criarColegiado(@RequestBody Colegiado colegiado){
        return ResponseEntity.status(HttpStatus.CREATED).body(colegiadoService.criarColegiado(colegiado));
    }

}
