package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {

    private static final String URL = "jdbc:mysql://localhost:3306/sistemaMercado";
    private static final String USUARIO = "root";
    private static final String SENHA = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection conectar() throws SQLException {
        Connection conexao = null;
        try {
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão estabelecida com sucesso!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado!");
            System.err.println("Mensagem: " + e.getMessage());
            throw new SQLException("Driver JDBC não encontrado: " + e.getMessage());
            
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados!");
            System.err.println("Mensagem: " + e.getMessage());
            throw e;
        }
        return conexao;
    }
    public static void desconectar(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão fechada com sucesso!");
                
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão!");
                System.err.println("Mensagem: " + e.getMessage());
            } finally {
                System.out.println("Tentativa de desconexão finalizada.");
            }
        }
    }
}