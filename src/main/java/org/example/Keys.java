package org.example;

import io.javalin.config.Key;
import org.example.Services.*;

public enum Keys {
    CLIENTE_SERVICE(new Key<ClienteService>("cliente-service")),
    BARBEIRO_SERVICE(new Key<BarbeiroService>("barbeiro-service")),
    ADMINISTRADOR_SERVICE(new Key<AdministradorService>("administrador-service")),
    AGENDAMENTO_SERVICE(new Key<AgendamentoService>("agendamento-service"));

    private final Key<?> k;

    <T> Keys(Key<T> key) {
        this.k = key;
    }

    public <T> Key<T> key() {
        @SuppressWarnings("unchecked")
        Key<T> typedKey = (Key<T>) this.k;
        return typedKey;
    }
}
