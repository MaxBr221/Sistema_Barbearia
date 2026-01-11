package org.example.Repositorys;

import org.example.Dominios.Barbeiro;
import java.util.Optional;

public interface BarbeiroRepository {
    Barbeiro buscarPorLogin(String login);

    Optional<Barbeiro> buscarBarbeiroPadrao();

    void atualizar(Barbeiro barbeiro);

}
