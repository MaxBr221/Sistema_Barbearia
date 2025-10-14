package org.example;

public class ClienteNaoAtivo extends RuntimeException {
    public ClienteNaoAtivo(String message) {
        super(message);
    }
}
