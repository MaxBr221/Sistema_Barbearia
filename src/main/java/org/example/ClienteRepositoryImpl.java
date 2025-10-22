package org.example;
import org.example.Dominios.Cliente;
import org.example.Repositorys.ClienteRepository;

import java.sql.*;
import java.util.*;

public class ClienteRepositoryImpl implements ClienteRepository {

    @Override
    public void cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (id, nome, telefone, email, ativo, senha) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, cliente.getId());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setBoolean(5, cliente.isAtivo());
            stmt.setString(6, cliente.getSenha());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, telefone = ?, email = ?, ativo = ?, senha = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());
            stmt.setBoolean(4, cliente.isAtivo());
            stmt.setString(5, cliente.getSenha());
            stmt.setObject(6, cliente.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                clientes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public Cliente buscarClientePorId(String id) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
