package org.example;

import org.example.Dominios.Servico;
import org.example.Repositorys.ServicoRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicoRepositoryImpl implements ServicoRepository {
    @Override
    public void adicionarServico(Servico servico) {
        String sql = "INSERT INTO Servico (id, nome, data ) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, servico.getId().toString());
            stmt.setString(2, servico.getNome());
            stmt.setObject(3, servico.getDuracao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Servico> listarServicos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM Servico";
        try(Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                Servico s = new Servico(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getTime("data").toLocalTime());
                servicos.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return servicos;
    }

    @Override
    public void removerServico(Servico servico) {
        String sql = "DELETE FROM servico WHERE id = ?";

        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, servico.getId().toString());
            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Removido com sucesso!");
            }else {
                System.out.println("Id não encontrado!");
            }

        }catch (SQLException e){
            System.out.println("Erro ao remover serviço: " + e.getMessage());
        }
    }
}
