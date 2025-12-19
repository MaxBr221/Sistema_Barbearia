package org.example.Exceptions;

public class ClienteAtivo extends RuntimeException {
    public ClienteAtivo(String message) {
        super(message);
    }
}
