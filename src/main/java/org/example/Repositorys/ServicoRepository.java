package org.example.Repositorys;

import org.example.Dominios.Servico;
import java.util.List;
import java.util.UUID;

public interface ServicoRepository {
    void adicionarServico(Servico servico);
    List<Servico> listarServicos();
    void removerServico(Servico servico);
    Servico buscarPorId(UUID id);


}
