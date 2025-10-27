package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TesteServicoRepository {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sistema_barbearia";
        String user = "root";
        String password = "12345678";

        try {
            Connection conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão bem-sucedida!");
            conexao.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }
}
