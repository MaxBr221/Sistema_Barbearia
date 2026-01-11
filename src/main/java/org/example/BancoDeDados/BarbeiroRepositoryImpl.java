package org.example.BancoDeDados;

import org.example.Dominios.Barbeiro;
import org.example.Repositorys.BarbeiroRepository;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class BarbeiroRepositoryImpl implements BarbeiroRepository {

    @Override
    public Barbeiro buscarPorLogin(String login) {
        String slq = "SELECT * FROM barbeiro WHERE email = ?";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(slq)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Barbeiro(

                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("senha"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar barbeiro por login" + e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Barbeiro> buscarBarbeiroPadrao() {
        String sql = "SELECT id, nome, telefone, email, senha FROM barbeiro LIMIT 1";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (!rs.next()) {
                return Optional.empty();
            }

            String idStr = rs.getString("id");
            if (idStr == null || idStr.isBlank()) {
                throw new RuntimeException("ID do barbeiro veio vazio do banco!");
            }

            return Optional.of(
                    new Barbeiro(
                            UUID.fromString(idStr),
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("senha")));

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar barbeiro padrão", e);
        }
    }

    @Override
    public void atualizar(Barbeiro barbeiro) {
        String sql = "UPDATE barbeiro SET nome = ?, telefone = ?, email = ?, senha = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barbeiro.getNome());
            stmt.setString(2, barbeiro.getTelefone());
            stmt.setString(3, barbeiro.getLogin());
            stmt.setString(4, barbeiro.getSenha());
            stmt.setString(5, barbeiro.getId().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar barbeiro", e);
        }
    }
}
