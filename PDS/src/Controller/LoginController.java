package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Model.*;
import View.*;

public class LoginController {

    private TelaLogin tela;

    public LoginController(TelaLogin tela) {
        this.tela = tela;

        this.tela.getBtnEntrar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entrar();
            }
        });

        this.tela.getBtnCadastrar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });
    }

    private void entrar() {
        try {
            String cpf = tela.getTxtCpf().getText();

            if (cpf == null || cpf.trim().isEmpty() || cpf.contains("_")) {
                JOptionPane.showMessageDialog(tela, 
                    "Por favor, insira um CPF válido!", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            UsuariosDAO dao = new UsuariosDAO();
            Usuarios usuario = dao.buscarPorCpf(cpf);

            if (usuario == null) {
                JOptionPane.showMessageDialog(tela, 
                    "Usuário não encontrado!\nVerifique o CPF ou cadastre-se.", 
                    "Login Falhou", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(tela, 
                "Bem-vindo(a), " + usuario.getNome() + "!", 
                "Login Realizado", 
                JOptionPane.INFORMATION_MESSAGE);

            tela.dispose();

            if (usuario.getIsAdmin()) {
                TelaCadastroProdutos telaProdutos = new TelaCadastroProdutos();
                telaProdutos.setVisible(true);
            } else {
                TelaCompra telaCompra = new TelaCompra(usuario);
                telaCompra.setVisible(true);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(tela, 
                "Erro de validação: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(tela, 
                "Erro ao fazer login: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(tela, 
                "Erro inesperado ao fazer login: " + ex.getMessage(), 
                "Erro Crítico", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cadastrar() {
        try {
            tela.dispose();
            
            TelaCadastro telaCadastro = new TelaCadastro();
            telaCadastro.setVisible(true);
            
        } catch (Exception e) {
            System.err.println("Erro ao abrir tela de cadastro: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(null, 
                "Erro ao abrir tela de cadastro: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}