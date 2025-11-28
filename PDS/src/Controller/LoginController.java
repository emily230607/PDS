package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Exception.*;
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
            String cpf = tela.getTxtCpf().getText().replace("_", "").trim();

            if (cpf.isEmpty() || cpf.length() < 11) {
                throw new CampoVazioException("CPF", "Por favor, preencha o CPF completamente.");
            }

            validarCPF(cpf);

            UsuariosDAO dao = new UsuariosDAO();
            Usuarios usuario = dao.buscarPorCpf(cpf);

            if (usuario == null) {
                JOptionPane.showMessageDialog(tela, 
                    "Usuário não encontrado!\nVerifique o CPF ou faça seu cadastro.", 
                    "Usuário Não Encontrado", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(tela, 
                "Login realizado com sucesso!\nBem-vindo(a), " + usuario.getNome() + "!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);

            tela.dispose();

            if (usuario.getIsAdmin()) {
                TelaCadastroProdutos telaProdutos = new TelaCadastroProdutos();
                telaProdutos.setVisible(true);
            } else {
                TelaCompra telaCompra = new TelaCompra(usuario);
                telaCompra.setVisible(true);
            }

        } catch (CampoVazioException ex) {
            JOptionPane.showMessageDialog(tela, 
                ex.getMessage(), 
                "Campo Vazio", 
                JOptionPane.WARNING_MESSAGE);
        } catch (CPFInvalidoException ex) {
            JOptionPane.showMessageDialog(tela, 
                ex.getMessage(), 
                "CPF Inválido", 
                JOptionPane.ERROR_MESSAGE);
        } catch (BancoDeDadosException ex) {
            JOptionPane.showMessageDialog(tela, 
                "Erro ao acessar o banco de dados:\n" + ex.getMessage(), 
                "Erro de Banco", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(tela, 
                "Erro inesperado ao fazer login:\n" + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void validarCPF(String cpf) throws CPFInvalidoException {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            throw new CPFInvalidoException("O CPF deve conter exatamente 11 dígitos.");
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            throw new CPFInvalidoException("CPF inválido. Todos os dígitos são iguais.");
        }

        if (!cpf.matches("\\d{11}")) {
            throw new CPFInvalidoException("O CPF deve conter apenas números.");
        }
    }

    private void cadastrar() {
        tela.dispose();
        TelaCadastro telaCadastro = new TelaCadastro();
        telaCadastro.setVisible(true);
    }
}