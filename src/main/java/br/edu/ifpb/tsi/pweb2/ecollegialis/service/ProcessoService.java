package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusProcesso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.VotoRepository;
import lombok.AllArgsConstructor;
import java.util.List;

@Service
@AllArgsConstructor

public class ProcessoService {
    
    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ProfessorService professorService;

    public ProcessoService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    public Processo buscarPorId(Long id) {
        return processoRepository.findById(id).get();
    }

    public Processo criarProcesso(){
        return this.processoRepository.save(new Processo());
    }

    public List<Processo> listarProcessos() {
        return processoRepository.findAll();
    }

    public void deletarProcesso(Processo processo){
        if (processo.getId() != null && this.processoRepository.existsById(processo.getId())) {
            this.processoRepository.delete(processo);
        } else {
            throw new RuntimeException("O processo não existe");
        }
    }

    public Processo alteraProcesso(Processo processo){
        if (processo.getId() != null && this.processoRepository.existsById(processo.getId())) {
            return this.processoRepository.save(processo);
        } else {
            throw new RuntimeException("O processo não existe");
        }
    }

    public void votar (Long id, String voto, String justificativa) {
        Processo processo = processoRepository.findById(id).get();
        TipoDecisao tipoDecisao = null;
        if (voto.equals("deferir")) {
            tipoDecisao = TipoDecisao.DEFERIDO;
        }
        else {
            tipoDecisao = TipoDecisao.INDEFERIDO;
        }

        processo.setTipoDecisao(tipoDecisao);
        processo.setTextoRelator(justificativa);
        processoRepository.save(processo);

    }

    public Processo atribuirProcesso (Processo processo, Long idProfessor) {
        processo.setRelator(professorService.buscarPorId(idProfessor));
        return processoRepository.save(processo);
    }

    public List<Processo> listarProcessosCoordenador (Long idCoordenador, StatusProcesso status) {
        return processoRepository.findAllByCoordenadorAndStatus(idCoordenador, status);
    }
}
