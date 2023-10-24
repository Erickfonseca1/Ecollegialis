package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusProcesso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import lombok.AllArgsConstructor;
import java.util.List;

@Service
@AllArgsConstructor
public class ProcessoService {

        @Autowired
        ProcessoRepository processoRepository;

        public List<Processo> listarProcessos() {
            return processoRepository.findAll();
        }

        public Processo buscarProcessoPorId(Long id) {
            return processoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));
        }

        public Processo criarProcesso(Processo processo) {
            return processoRepository.save(processo);
        }

        public void deletarProcesso(Long id) {
            Processo processo = processoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));
            processoRepository.delete(processo);
        }

        public Processo atualizarProcesso(Processo processoAtualizado, Long id) {
            Processo processo = processoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));
            processo.setNumero(processoAtualizado.getNumero());
            processo.setDataRecepcao(processoAtualizado.getDataRecepcao());
            processo.setDataDistribuicao(processoAtualizado.getDataDistribuicao());
            processo.setTextoRelator(processoAtualizado.getTextoRelator());
            processo.setDataParecer(processoAtualizado.getDataParecer());
            processo.setTextoAluno(processoAtualizado.getTextoAluno());
            processo.setParecer(processoAtualizado.getParecer());
            processo.setStatus(processoAtualizado.getStatus());
            processo.setTipoDecisao(processoAtualizado.getTipoDecisao());
            processo.setAssunto(processoAtualizado.getAssunto());
            processo.setVotos(processoAtualizado.getVotos());
            processo.setAluno(processoAtualizado.getAluno());
            processo.setEmPauta(processoAtualizado.isEmPauta());
            processo.setProfessor(processoAtualizado.getProfessor());
            processo.setAnexos(processoAtualizado.getAnexos());
            return processoRepository.save(processo);
        }


}