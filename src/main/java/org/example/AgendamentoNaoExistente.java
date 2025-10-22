package org.example;

public class AgendamentoNaoExistente extends RuntimeException {
    public AgendamentoNaoExistente(String message) {
        super(message);
    }
}
