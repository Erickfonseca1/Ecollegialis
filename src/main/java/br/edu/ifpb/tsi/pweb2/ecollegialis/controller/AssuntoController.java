package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assuntos")
public class AssuntoController {

    @Autowired
    private AssuntoService assuntoService;

    @PostMapping("/cadastrar-assunto")
    public ResponseEntity<Object> criarAssunto(@RequestBody Assunto assunto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assuntoService.criarAssunto(assunto));
    }

    @GetMapping("/buscar-por-nome/{nome}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Assunto buscarAssuntoPorNome(@PathVariable String nome) {
        return assuntoService.buscarAssuntoPorNome(nome);
    }

    @DeleteMapping("/deletar-por-nome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deletarAssuntoPorNome(@PathVariable String nome) {
        assuntoService.deletarAssuntoPorNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body("Assunto deletado com sucesso!");
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Assunto atualizarAssunto(@RequestBody Assunto assuntoAtualizado) {
        return assuntoService.atualizarAssunto(assuntoAtualizado);
    }

}
