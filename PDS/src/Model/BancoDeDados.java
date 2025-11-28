package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {

    private static final String URL = "jdbc:mysql://localhost:3306/sistemaMercado";
    private static final String USUARIO = "root";
    private static final String SENHA = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Conecta ao banco de dados com tratamento de exceções checked
     * Usa try-catch conforme ensinado na aula
     */
    public static Connection conectar() throws SQLException {
        Connection conexao = null;
        try {
            // Tenta carregar o driver JDBC
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("✅ Conexão estabelecida com sucesso!");
            
        } catch (ClassNotFoundException e) {
            // Exceção checked: ClassNotFoundException
            System.err.println("Erro: Driver JDBC não encontrado!");
            System.err.println("Mensagem: " + e.getMessage());
            throw new SQLException("Driver JDBC não encontrado: " + e.getMessage());
            
        } catch (SQLException e) {
            // Exceção checked: SQLException
            System.err.println("Erro ao conectar ao banco de dados!");
            System.err.println("Mensagem: " + e.getMessage());
            throw e; // Relança a exceção para o chamador tratar
        }
        return conexao;
    }

    /**
     * Desconecta do banco de dados com tratamento de exceções
     * Usa try-catch-finally conforme ensinado na aula
     */
    public static void desconectar(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("✅ Conexão fechada com sucesso!");
                
            } catch (SQLException e) {
                // Exceção checked: SQLException ao fechar conexão
                System.err.println("Erro ao fechar a conexão!");
                System.err.println("Mensagem: " + e.getMessage());
            } finally {
                // Finally sempre executa (exemplo da aula)
                System.out.println("Tentativa de desconexão finalizada.");
            }
        }
    }
}