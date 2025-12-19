package org.example.Exceptions;

public class ClienteNaoAtivo extends RuntimeException {
    public ClienteNaoAtivo(String message) {
        super(message);
    }
}
