package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusProcesso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/processo")
@AllArgsConstructor
public class ProcessoController {

    private final ProcessoService processoService;

    @PostMapping("/criar")
    @ResponseBody
    public Processo criarProcesso() {
        return processoService.criarProcesso();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Processo buscarPorId(@PathVariable Long id) {
        return processoService.buscarPorId(id);
    }

    @GetMapping("/listar")
    @ResponseBody
    public List<Processo> listarProcessos() {
        return processoService.listarProcessos();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletarProcesso(@PathVariable Long id) {
        Processo processo = processoService.buscarPorId(id);
        processoService.deletarProcesso(processo);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Processo alterarProcesso(@PathVariable Long id, @RequestBody Processo processo) {
        processo.setId(id);
        return processoService.alteraProcesso(processo);
    }

    @PostMapping("/{id}/votar")
    @ResponseBody
    public void votar(@PathVariable Long id, @RequestParam String voto, @RequestParam String justificativa) {
        processoService.votar(id, voto, justificativa);
    }

    @PostMapping("/{id}/atribuir")
    @ResponseBody
    public Processo atribuirProcesso(@PathVariable Long id, @RequestParam Long idProfessor) {
        Processo processo = processoService.buscarPorId(id);
        return processoService.atribuirProcesso(processo, idProfessor);
    }

    @GetMapping("/listarCoordenador/{idCoordenador}/{status}")
    @ResponseBody
    public List<Processo> listarProcessosCoordenador(@PathVariable Long idCoordenador, @PathVariable StatusProcesso status) {
        return processoService.listarProcessosCoordenador(idCoordenador, status);
    }
}
