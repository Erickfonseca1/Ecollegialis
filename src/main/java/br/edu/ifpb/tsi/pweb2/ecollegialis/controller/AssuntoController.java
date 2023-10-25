package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assuntos")
public class AssuntoController {

    @Autowired
    private AssuntoService assuntoService;

    @PostMapping("/criar")
    public ResponseEntity<Object> criarAssunto(@RequestBody Assunto assunto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assuntoService.criarAssunto(assunto));
    }

    @GetMapping("/{id}")
    public Assunto buscarAssunto(@PathVariable("id") Long id) {
        return assuntoService.buscarAssuntoPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Assunto>> listagemAssunto() {
        return ResponseEntity.status(HttpStatus.OK).body(assuntoService.listarAssuntos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarAssunto(@PathVariable (value = "id") Long id){
        var assuntoExistente = assuntoService.buscarAssuntoPorId(id);
        assuntoService.deletarAssunto(assuntoExistente);
        return ResponseEntity.status(HttpStatus.OK).body("OK: Assunto excluído com sucesso!");
    }


    @PutMapping("/atualizar/{id}")
    public Assunto atualizarAssunto(@RequestBody Assunto assuntoAtualizado) {
        return assuntoService.atualizarAssunto(assuntoAtualizado);
    }

}
