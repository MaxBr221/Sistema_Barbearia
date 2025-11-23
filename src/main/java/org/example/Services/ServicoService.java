package org.example.Services;

import org.example.Repositorys.ServicoRepository;
import org.example.Dominios.Servico;

import java.util.List;
import java.util.UUID;

public class ServicoService {
    private ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public void adicionarServico(Servico servico){
        if (servico == null){
            throw new IllegalArgumentException("Erro, servico não pode ser nulo!");
        }
        servicoRepository.adicionarServico(servico);
    }

    public List<Servico> listarServicos(){
        return servicoRepository.listarServicos();
    }
    public void removerServico(Servico servico){
        if (servico == null){
            throw new IllegalArgumentException("Erro, servico não pode ser nulo!");
        }
        servicoRepository.removerServico(servico);
    }
    public Servico buscarPorId(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Erro, valor nulo.");
        }
        return servicoRepository.buscarPorId(id);
    }
}
