package org.example.BancoDeDados;

import org.example.Dominios.Agendamento;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.Dominios.Status;
import org.example.Repositorys.BarbeiroRepository;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BarbeiroRepositoryImpl implements BarbeiroRepository {

    @Override
    public List<Agendamento> listarAgendamentos() {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT * FROM Agendamento";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                LocalDate data = rs.getDate("data").toLocalDate();
                LocalTime hora = rs.getTime("hora").toLocalTime();

                Agendamento ag = new Agendamento();
                ag.setId(id);
                ag.setData(data);
                ag.setHora(hora);
                ag.setStatus(Status.valueOf(rs.getString("status")));

                agendamentos.add(ag);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos: " + e.getMessage(), e);
        }

        return agendamentos;
    }

    @Override
    public List<Cliente> listarCliente() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        }

        return clientes;
    }

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
    public void removerCLiente(UUID id) {
        String sql = "DELETE * FROM Cliente WHERE id = ?";
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, id.toString());
            int linhaAfetadas = stmt.executeUpdate();

            if (linhaAfetadas > 0){
                System.out.println(linhaAfetadas + " linhas afetadas!, cliente removido com sucesso!");
            }else{
                System.out.println("Cliente não encontrado!");
            }
        }catch (SQLException e){
            System.out.println("Erro ao remover cliente" + e.getMessage());
        }

    }
}
