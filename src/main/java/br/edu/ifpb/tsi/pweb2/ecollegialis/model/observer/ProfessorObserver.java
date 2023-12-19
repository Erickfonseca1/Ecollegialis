package br.edu.ifpb.tsi.pweb2.ecollegialis.model.observer;

import org.springframework.stereotype.Component;

@Component
public class ProfessorObserver extends AbstractObserver {

    private String lastMessage;

    @Override
    public void update(String message) {
        // Lógica específica para o ProfessorObserver ao receber uma notificação do Subject
        lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
