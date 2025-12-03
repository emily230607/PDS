package Controller;

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

        this.view.cadastrar(e -> {
            cadastrarUsuario();
        });

        this.view.cancelar(e -> {
            voltarParaLogin();
        });
    }

    private void cadastrarUsuario() {
        try {
            String nome = view.getNome();
            String cpf = view.getCPF();
            boolean isAdmin = view.isAdmin();

            if (nome == null || nome.trim().isEmpty()) {
                view.exibirMensagem("Erro", "Por favor, preencha o nome!", 0);
                return;
            }

            if (cpf == null || cpf.contains("_") || cpf.trim().isEmpty()) {
                view.exibirMensagem("Erro", "Por favor, preencha o CPF corretamente!", 0);
                return;
            }

            Usuarios usuario = new Usuarios(nome, cpf, isAdmin);
            
            model.adicionarUsuario(usuario);

            view.exibirMensagem("Sucesso", "Usuário cadastrado com sucesso!", 1);
            view.limparCampos();

            view.dispose();
            
            TelaLogin login = new TelaLogin();
            login.setVisible(true);

        } catch (IllegalArgumentException e) {
            view.exibirMensagem("Erro de Validação", e.getMessage(), 0);
            
        } catch (RuntimeException e) {
            view.exibirMensagem("Erro", "Erro ao cadastrar: " + e.getMessage(), 0);
            
        } catch (Exception e) {
            view.exibirMensagem("Erro Inesperado", "Erro inesperado: " + e.getMessage(), 0);
            e.printStackTrace();
        }
    }

    private void voltarParaLogin() {
        try {
            view.dispose();
            
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
            
        } catch (Exception e) {
            System.err.println("Erro ao voltar para login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}