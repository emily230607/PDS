package Controller;

import View.TelaLogin;
import Model.BancoDeDados;

public class Main {
    public static void main(String[] args) {
        try {
            BancoDeDados.conectar();
            System.out.println("✅ Banco conectado com sucesso!");
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao conectar com o banco: " + e.getMessage());
        } finally {
            BancoDeDados.desconectar(null);
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        });
    }
}