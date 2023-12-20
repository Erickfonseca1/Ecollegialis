package br.edu.ifpb.tsi.pweb2.ecollegialis.observer;

public class AlunoObserver extends AbstractObserver {
    private String lastMessage;

    @Override
    public void update(String message) {
        lastMessage = "Aluno: " + message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
