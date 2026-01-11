package org.example.BancoDeDados;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = Database.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null)
            url = properties.getProperty("db.url");
        if (url == null || url.startsWith("${"))
            url = "jdbc:mysql://localhost:3307/sistema_barbearia?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

        if (user == null)
            user = properties.getProperty("db.usuario");
        if (user == null || user.startsWith("${"))
            user = "root";

        if (password == null)
            password = properties.getProperty("db.senha");
        if (password == null || password.startsWith("${"))
            password = "12345678";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não encontrado!", e);
        }

        return DriverManager.getConnection(url, user, password);
    }
}
