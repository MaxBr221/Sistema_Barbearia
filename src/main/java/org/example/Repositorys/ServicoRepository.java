package org.example.Repositorys;

import org.example.Servico;
import java.util.List;

public interface ServicoRepository {
    void adicionarServico(Servico servico);
    List<Servico> listarServicos();
    void removerServico(Servico servico);


}
