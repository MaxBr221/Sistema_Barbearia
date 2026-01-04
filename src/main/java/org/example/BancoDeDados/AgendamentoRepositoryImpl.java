package org.example.BancoDeDados;

import org.example.Dominios.*;
import org.example.Repositorys.AgendamentoRepository;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


public class AgendamentoRepositoryImpl implements AgendamentoRepository {
    @Override
    public void adicionarAgendamento(Agendamento agendamento) {
        String sql = "INSERT INTO Agendamento (id, dat, hora, barbeiro_id, cliente_id, status, tipo_servico) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, agendamento.getId().toString());
            stmt.setDate(2, Date.valueOf(agendamento.getData()));
            stmt.setTime(3, Time.valueOf(agendamento.getHora()));
            stmt.setString(4, agendamento.getBarbeiro().getId().toString());
            stmt.setString(5, agendamento.getCliente().getId().toString());
            stmt.setString(6, agendamento.getStatus().name());
            stmt.setString(7, agendamento.getTipoServico().name());
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
        String sql = """
        SELECT 
            a.id            AS ag_id,
            a.dat           AS ag_data,
            a.hora          AS ag_hora,
            a.status        AS ag_status,
            a.tipo_servico  AS ag_tipo_servico,

            c.id            AS cliente_id,
            c.nome          AS cliente_nome,

            b.id            AS barbeiro_id
        FROM agendamento a
        JOIN cliente c   ON c.id = a.cliente_id
        JOIN barbeiro b  ON b.id = a.barbeiro_id
    """;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Barbeiro barbeiro = new Barbeiro(UUID.fromString(rs.getString("barbeiro_id")));
                Cliente cliente = new Cliente(UUID.fromString(rs.getString("cliente_id")),
                        rs.getString("cliente_nome"));

                Agendamento agendamento = new Agendamento(
                        UUID.fromString(rs.getString("ag_id")),
                        rs.getDate("ag_data").toLocalDate(),
                        rs.getTime("ag_hora").toLocalTime(),
                        barbeiro,
                        cliente,
                        Status.valueOf(rs.getString("ag_status")),
                        TipoServico.valueOf(rs.getString("ag_tipo_servico"))
                );
                agendamentos.add(agendamento);
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
                return new Agendamento(
                        UUID.fromString(rs.getString("id")),
                        rs.getDate("dat").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        barbeiro,
                        cliente,
                        Status.valueOf(rs.getString("status")),
                        TipoServico.valueOf(rs.getString("tipo_servico")));

            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscarAgendamento" + e.getMessage());
        }return null;
    }

    @Override
    public boolean existeAgendamento(LocalDate localDate, LocalTime localTime) {
        String sql = """
        SELECT COUNT(*) 
        FROM Agendamento 
        WHERE dat = ? 
          AND hora = ?
          AND status <> 'CANCELADO'
    """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(localDate));
            stmt.setTime(2, Time.valueOf(localTime));

            ResultSet rs = stmt.executeQuery();
            rs.next();

            return rs.getInt(1) > 0;



        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar agendamento", e);
        }
    }


    @Override
    public List<LocalTime> buscarHorariosOcupadosPorData(LocalDate data) {
        List<LocalTime> horarios = new ArrayList<>();

        String sql = """
        SELECT hora
        FROM Agendamento
        WHERE dat = ?
          AND status <> 'CANCELADO'
    """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, data);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                horarios.add(rs.getTime("hora").toLocalTime());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar horários ocupados", e);
        }

        return horarios;
    }
    @Override
    public List<Agendamento> listarAgendamentoAtivos(UUID clienteId) {
        String sql = """
    SELECT
       a.id AS ag_id,
       a.dat,
       a.hora,
       a.status,
       a.tipo_servico,

       c.id AS c_id,
       c.nome,
       c.telefone,
       c.login,
       c.senha,

       b.id AS b_id,
       b.nome AS b_nome,
       b.telefone AS b_telefone,
       b.login AS b_login,
       b.senha AS b_senha

   FROM agendamento a
   JOIN cliente c ON c.id = a.cliente_id
   JOIN barbeiro b ON b.id = a.barbeiro_id
   WHERE a.cliente_id = ?
     AND a.status = 'RESERVADO'
     AND CONCAT(a.dat, ' ', a.hora) >= NOW()
   ORDER BY a.dat, a.hora;
""";

        List<Agendamento> agendamentos = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clienteId.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente(
                        UUID.fromString(rs.getString("c_id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha")
                );

                Barbeiro barbeiro = new Barbeiro(
                        UUID.fromString(rs.getString("b_id")),
                        rs.getString("b_nome"),
                        rs.getString("b_telefone"),
                        rs.getString("b_login"),
                        rs.getString("b_senha")
                );

                Agendamento agendamento = new Agendamento(UUID.fromString(rs.getString("ag_id")),
                        rs.getDate("dat").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        barbeiro,
                        cliente,
                        Status.valueOf(rs.getString("status")),
                        TipoServico.valueOf(rs.getString("tipo_servico")));
                agendamentos.add(agendamento);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos ativos", e);
        }

        return agendamentos;
    }

    @Override
    public List<Agendamento> listarHistorico(UUID clienteId) {
        String sql = """
    SELECT
        a.id AS ag_id,
        a.dat,
        a.hora,
        a.status,
        a.tipo_servico,
    
        c.id AS c_id,
        c.nome, c.telefone,
        c.login,
        c.senha,
    
        b.id AS b_id,
        b.nome AS b_nome,
        b.telefone AS b_telefone,
        b.login AS b_login,
        b.senha AS b_senha
    
    FROM agendamento a
    JOIN cliente c ON c.id = a.cliente_id
    JOIN barbeiro b ON b.id = a.barbeiro_id
    WHERE a.cliente_id = ?
        AND a.status = 'RESERVADO'
        ORDER BY a.dat, a.hora;""";
        List<Agendamento> listarHistorico = new ArrayList<>();
        try(Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clienteId.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        UUID.fromString(rs.getString("c_id")),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha")
                );

                Barbeiro barbeiro = new Barbeiro(
                        UUID.fromString(rs.getString("b_id")),
                        rs.getString("b_nome"),
                        rs.getString("b_telefone"),
                        rs.getString("b_login"),
                        rs.getString("b_senha")
                );

                Agendamento agendamento = new Agendamento(UUID.fromString(rs.getString("ag_id")),
                        rs.getDate("dat").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        barbeiro,
                        cliente,
                        Status.valueOf(rs.getString("status")),
                        TipoServico.valueOf(rs.getString("tipo_servico")));
                listarHistorico.add(agendamento);
            }
        }catch (SQLException e){
            throw new RuntimeException("Erro ao listar histórico");
        }return listarHistorico;
    }
}
