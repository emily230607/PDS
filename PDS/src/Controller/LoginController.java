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
        String cpf = tela.getTxtCpf().getText();

        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(tela, "Por favor, insira o CPF.");
            return;
        }

        try {
            var conn = BancoDeDados.conectar();
            UsuariosDAO dao = new UsuariosDAO();
            Usuarios usuario = dao.buscarPorCpf(cpf);
            BancoDeDados.desconectar(conn);

            if (usuario == null) {
                JOptionPane.showMessageDialog(tela, "Usuário não encontrado!");
                return;
            }

            JOptionPane.showMessageDialog(tela, "Login realizado com sucesso!");

            tela.dispose();

            if (usuario.getIsAdmin()) {
                new TelaCadastroProdutos().setVisible(true);
            } else {
                new TelaCompra(usuario).setVisible(true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(tela, "Erro ao fazer login: " + ex.getMessage());
        }
    }

    private void cadastrar() {
        tela.dispose();
        new TelaCadastro().setVisible(true);
    }
}
