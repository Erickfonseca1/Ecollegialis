package br.edu.ifpb.tsi.pweb2.ecollegialis.ecollegialis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.observer.AlunoObserver;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.observer.CoordenadorObserver;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.observer.ProfessorObserver;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.observer.Subject;

@SpringBootTest
public class SubjectTest {

    @Autowired
    private Subject subject;

    @Test
    public void testNotifyObservers() {
        AlunoObserver alunoObserver = new AlunoObserver();
        CoordenadorObserver coordenadorObserver = new CoordenadorObserver();
        ProfessorObserver professorObserver = new ProfessorObserver();

        // Verifica a lista de observadores vazia antes de registrar
        assertThat(subject.getObservers()).isEmpty();

        // Registra os observadores
        subject.attach(alunoObserver);
        subject.attach(coordenadorObserver);
        subject.attach(professorObserver);

        // Verifica se os observadores foram registrados corretamente
        assertThat(subject.getObservers()).hasSize(3);

        // Notifica os observadores
        subject.notifyObservers("Solicitação de Matrícula");

        // Testa o comportamento com outra mensagem
        subject.notifyObservers("Nova nota cadastrada...");

        // Verifica as mensagens recebidas pelos observadores
        assertThat(alunoObserver.getLastMessage()).endsWith("Nova nota cadastrada...");
        assertThat(coordenadorObserver.getLastMessage()).endsWith("Nova nota cadastrada...");
        assertThat(professorObserver.getLastMessage()).endsWith("Nova nota cadastrada...");
    }
}
