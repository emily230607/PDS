package Controller;

import View.TelaLogin;
import Model.BancoDeDados;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Classe principal com tratamento completo de exceções
 * Segue o padrão ensinado na Aula 06
 */
public class Main {
    
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("⚠️ Não foi possível configurar Look and Feel: " + e.getMessage());
        }
        
        Connection conexaoTeste = null;
        
        try {
            System.out.println("🔄 Tentando conectar ao banco de dados...");
            
            conexaoTeste = BancoDeDados.conectar();
            
            if (conexaoTeste == null) {
                throw new SQLException("Falha ao estabelecer conexão!");
            }
            
            System.out.println("Banco conectado com sucesso!");
            System.out.println("Sistema pronto para uso.\n");
            
        } catch (SQLException e) {
            System.err.println("ERRO CRÍTICO: Não foi possível conectar ao banco!");
            System.err.println("Detalhes: " + e.getMessage());
            System.err.println("\n Verifique:");
            System.err.println("   1. Se o MySQL está rodando");
            System.err.println("   2. Se o banco 'sistemaMercado' existe");
            System.err.println("   3. Usuário e senha em BancoDeDados.java");
            System.err.println("   4. Driver JDBC está no classpath\n");
            
            JOptionPane.showMessageDialog(null,
                "Erro ao conectar ao banco de dados!\n\n" +
                "Verifique se o MySQL está rodando e\n" +
                "se as configurações estão corretas.\n\n" +
                "Detalhes: " + e.getMessage(),
                "Erro Crítico de Conexão",
                JOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
            
        } catch (Exception e) {
            System.err.println("❌ Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(null,
                "Erro inesperado ao iniciar sistema:\n" + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
            
        } finally {
            BancoDeDados.desconectar(conexaoTeste);
            System.out.println("🔒 Conexão de teste finalizada.\n");
        }
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("🚀 Iniciando interface gráfica...");
                    TelaLogin tela = new TelaLogin();
                    tela.setVisible(true);
                    System.out.println("✅ Sistema iniciado com sucesso!\n");
                    
                } catch (Exception e) {
                    System.err.println("❌ Erro ao criar interface: " + e.getMessage());
                    e.printStackTrace();
                    
                    JOptionPane.showMessageDialog(null,
                        "Erro ao iniciar interface gráfica:\n" + e.getMessage(),
                        "Erro de Interface", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}