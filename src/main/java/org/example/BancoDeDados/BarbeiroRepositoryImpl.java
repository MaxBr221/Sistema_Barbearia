package org.example.BancoDeDados;

import org.example.Dominios.Barbeiro;
import org.example.Repositorys.BarbeiroRepository;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;


public class BarbeiroRepositoryImpl implements BarbeiroRepository {

    @Override
    public Barbeiro buscarPorLogin(String login) {
        String slq = "SELECT * FROM Barbeiro WHERE login = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(slq)){

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new Barbeiro(

                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"));
            }
        }catch (SQLException e){
            System.out.println("Erro ao buscar barbeiro por login" + e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Barbeiro> buscarBarbeiroPadrao() {
        String sql = "SELECT id, nome, telefone, login, senha FROM barbeiro LIMIT 1";
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
                            rs.getString("login"),
                            rs.getString("senha")
                    )
            );

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar barbeiro padrão", e);
        }
    }
}
