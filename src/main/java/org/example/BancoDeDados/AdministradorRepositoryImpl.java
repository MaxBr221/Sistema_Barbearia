package org.example.BancoDeDados;

import org.example.Dominios.Administrador;
import org.example.Repositorys.AdministradorRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.UUID;

public class AdministradorRepositoryImpl implements AdministradorRepository {
    @Override
    public Administrador buscarPorLogin(String login) {
        String slq = "SELECT * FROM Administrador WHERE login = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(slq)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Administrador(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar administrador por login" + e.getMessage());
        }
        return null;
    }
}