package Controller;

import javax.swing.JOptionPane;
import Exception.*;
import Model.Usuarios;
import Model.UsuariosDAO;
import View.TelaCadastro;
import View.TelaLogin;

public class CadastroController {
    private final TelaCadastro view;
    private final UsuariosDAO model;

    public CadastroController(TelaCadastro view) {
        this.view = view;
        this.model = new UsuariosDAO();

        this.view.cadastrar(e -> cadastrarUsuario());
        this.view.cancelar(e -> voltarParaLogin());
    }

    private void cadastrarUsuario() {
        try {
            validarCampos();
            
            String nome = view.getNome().trim();
            String cpf = view.getCPF().replace("_", "").trim();
            boolean isAdmin = view.isAdmin();

            validarCPF(cpf);

            Usuarios usuario = new Usuarios(nome, cpf, isAdmin);
            model.adicionarUsuario(usuario);

            view.exibirMensagem("Sucesso", "Usuário cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
            view.limparCampos();
            voltarParaLogin();

        } catch (CampoVazioException ex) {
            view.exibirMensagem("Campos Vazios", ex.getMessage(), JOptionPane.WARNING_MESSAGE);
        } catch (CPFInvalidoException ex) {
            view.exibirMensagem("CPF Inválido", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (UsuarioDuplicadoException ex) {
            view.exibirMensagem("Usuário Duplicado", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (BancoDeDadosException ex) {
            view.exibirMensagem("Erro no Banco de Dados", 
                "Não foi possível cadastrar o usuário.\n" + ex.getMessage(), 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            view.exibirMensagem("Erro Inesperado", 
                "Ocorreu um erro inesperado: " + ex.getMessage(), 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void validarCampos() throws CampoVazioException {
        String nome = view.getNome().trim();
        String cpf = view.getCPF().replace("_", "").trim();

        if (nome.isEmpty()) {
            throw new CampoVazioException("Nome", "O campo nome não pode estar vazio.");
        }

        if (cpf.isEmpty() || cpf.length() < 11) {
            throw new CampoVazioException("CPF", "O campo CPF deve ser preenchido completamente.");
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

    private void voltarParaLogin() {
        view.dispose();
        TelaLogin login = new TelaLogin();
        new LoginController(login);
        login.setVisible(true);
    }
}