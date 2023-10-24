package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ColegiadoService;
import lombok.AllArgsConstructor;

import java.util.List;

@Controller
@RequestMapping("/colegiado")
@AllArgsConstructor
public class ColegiadoController {

    private final ColegiadoService colegiadoService;

    @GetMapping("/listar")
    @ResponseBody
    public List<Colegiado> listarColegiados() {
        return colegiadoService.listarColegiados();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Colegiado buscarPorId(@PathVariable Long id) {
        return colegiadoService.buscarPorId(id);
    }

    @PostMapping("/criar")
    @ResponseBody
    public Colegiado criarColegiado(@RequestBody Colegiado colegiado) {
        return colegiadoService.criarColegiado(colegiado);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletarColegiado(@PathVariable Long id) {
        Colegiado colegiado = colegiadoService.buscarPorId(id);
        colegiadoService.deletarColegiado(colegiado);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Colegiado alterarColegiado(@PathVariable Long id, @RequestBody Colegiado colegiado) {
        colegiado.setId(id);
        return colegiadoService.alterarColegiado(colegiado);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public Colegiado atualizarColegiado(@PathVariable Long id, @RequestBody Colegiado colegiado) {
        return colegiadoService.atualizarColegiado(id, colegiado);
    }
}
