package org.example;

import org.example.Dominios.*;
import org.example.Repositorys.AgendamentoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class AgendamentoRepositoryImpl implements AgendamentoRepository {
    @Override
    public void adicionarAgendamento(Agendamento agendamento) {
        String sql = "INSERT INTO Agendamento (id, data, hora, barbeiro, cliente, servico, status, tipoServico) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, agendamento.getId().toString());
            stmt.setDate(2, Date.valueOf(agendamento.getData()));
            stmt.setTime(3, Time.valueOf(agendamento.getHora()));
            stmt.setString(4, agendamento.getBarbeiro().getId().toString());
            stmt.setString(5, agendamento.getCliente().getId().toString());
            stmt.setString(6, agendamento.getServico().getId().toString());
            stmt.setString(7, agendamento.getStatus().name());
            stmt.setString(8, agendamento.getTipoServico().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar " + e.getMessage());
        }

    }

    @Override
    public void removerAgendamento(UUID id) {
        String sql = "DELETE FROM agendamento WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            int linhaAfetada = stmt.executeUpdate();

            if (linhaAfetada > 0) {
                System.out.println("Removido com sucesso");
            } else {
                System.out.println("Erro, id incorreto!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover agendamento" + e.getMessage());
        }

    }

    @Override
    public List<Agendamento> listarAgendamentos() {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT * FROM Agendamento";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Barbeiro barbeiro = new Barbeiro(UUID.fromString(rs.getString("barbeiro_id")));
                Cliente cliente = new Cliente(UUID.fromString(rs.getString("cliente_id")));
                Servico servico = new Servico(UUID.fromString(rs.getString("servico_id")));
                Agendamento ag = new Agendamento(
                        UUID.fromString(rs.getString("id")),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        barbeiro,
                        cliente,
                        servico,
                        Status.valueOf(rs.getString("status")),
                        TipoServico.valueOf(rs.getString("tipoServico")));
                agendamentos.add(ag);
            }
        } catch (SQLException e) {
            System.out.println("Erro na listagem de agendamentos" + e.getMessage());
        }
        return agendamentos;
    }

    @Override
    public Agendamento buscarAgendamentoPorId(UUID id) {
        String sql = "SELECT * FROM Agendamento WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Barbeiro barbeiro = new Barbeiro(UUID.fromString(rs.getString("barbeiro_id")));
                Cliente cliente = new Cliente(UUID.fromString(rs.getString("cliente_id")));
                Servico servico = new Servico(UUID.fromString(rs.getString("servico_id")));
                return new Agendamento(
                        UUID.fromString(rs.getString("id")),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        barbeiro,
                        cliente,
                        servico,
                        Status.valueOf(rs.getString("status")),
                        TipoServico.valueOf(rs.getString("tipoServico")));

            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscarAgendamento" + e.getMessage());
        }return null;
    }
}
