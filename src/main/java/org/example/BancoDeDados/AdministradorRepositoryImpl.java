package org.example.BancoDeDados;

import org.example.Dominios.Administrador;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.Repositorys.AdministradorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdministradorRepositoryImpl implements AdministradorRepository {
    @Override
    public void cadastrarBarbeiro(Barbeiro barbeiro) {
        String sql = "INSERT INTO Barbeiro (id, nome, telefone, login, senha) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, barbeiro.getId().toString());
            stmt.setString(2, barbeiro.getNome());
            stmt.setString(3, barbeiro.getTelefone());
            stmt.setString(4, barbeiro.getLogin());
            stmt.setString(5, barbeiro.getSenha());
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar Barbeiro " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while (rs.next()){
                Cliente c = new Cliente(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"));
                clientes.add(c);
            }
        }catch (SQLException e) {
            System.out.println("Erro ao listar cliente " + e.getMessage());
        }return clientes;
    }

    @Override
    public void cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (id, nome, telefone, login, senha) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, cliente.getId().toString());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getLogin());
            stmt.setString(5, cliente.getSenha());
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar Cliente " + e.getMessage());
        }
    }

    @Override
    public Cliente buscarClientePorId(UUID id) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new Cliente(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"));

            }
        }catch (SQLException e){
            System.out.println("Erro ao buscar cliente " + e.getMessage());
        }return null;
    }

    @Override
    public Barbeiro buscarBarbeiroPorId(UUID id) {
        String sql = "SELECT * FROM Barbeiro WHERE id = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, id.toString());
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
            System.out.println("Erro ao buscar barbeiro " + e.getMessage());
        }return null;
    }

    @Override
    public void removerBarbeiro(UUID id) {
        String sql = "DELETE FROM Barbeiro WHERE id = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, id.toString());
            int linhaAfetadas = stmt.executeUpdate();
            if(linhaAfetadas > 0){
                System.out.println("Barbeiro removido com sucesso!");
            }else{
                System.out.println("id não encontrado.");
            }
        }catch (SQLException e){
            System.out.println("Erro ao remover barbeiro " + e.getMessage());
        }
    }

    @Override
    public void removerCliente(UUID id) {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, id.toString());
            int linhaAfetada = stmt.executeUpdate();

            if(linhaAfetada > 0){
                System.out.println("Cliente removido com sucesso!");
            }else {
                System.out.println("Id não encontrado.");
            }
        }catch (SQLException e){
            System.out.println("Erro ao remover cliente " + e.getMessage());
        }
    }

    @Override
    public List<Barbeiro> listarBarbeiros() {
        List<Barbeiro> barbeiros = new ArrayList<>();
        String sql = "SELECT * FROM Barbeiro";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                Barbeiro b = new Barbeiro(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"));
                barbeiros.add(b);
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar barbeiro " + e.getMessage());
        }return barbeiros;
    }

    @Override
    public Administrador buscarPorLogin(String login) {
        String slq = "SELECT * FROM Administrador WHERE login = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(slq)){

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new Administrador(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"));
            }
        }catch (SQLException e){
            System.out.println("Erro ao buscar administrador por login" + e.getMessage());
        }
        return null;
    }

    @Override
    public Barbeiro buscarBarbeiroPorLogin(String login) {
        String sql = "SELECT * FROM Barbeiro WHERE id = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

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
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    @Override
    public Cliente buscarClientePorLogin(String login) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new Cliente(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"));

            }
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
