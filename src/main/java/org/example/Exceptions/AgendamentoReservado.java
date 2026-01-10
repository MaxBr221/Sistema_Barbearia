package org.example.Exceptions;

public class AgendamentoReservado extends RuntimeException {
    public AgendamentoReservado(String message) {
        super(message);
    }
}
