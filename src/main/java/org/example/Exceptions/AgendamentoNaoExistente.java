package org.example.Exceptions;

public class AgendamentoNaoExistente extends RuntimeException {
    public AgendamentoNaoExistente(String message) {
        super(message);
    }
}
