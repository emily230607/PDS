package Controller;

import java.sql.Connection;
import Model.BancoDeDados;
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
            String nome = view.getNome();
            String cpf = view.getCPF();
            boolean isAdmin = view.isAdmin();

            if (nome.isEmpty() || cpf.contains("_")) {
                view.exibirMensagem("Erro", "Preencha todos os campos corretamente!", 0);
                return;
            }

            try {
                Usuarios usuario = new Usuarios(nome, cpf, isAdmin);
                model.adicionarUsuario(usuario);

                view.exibirMensagem("Sucesso", "Usuário cadastrado com sucesso!", 1);
                view.limparCampos();

                view.dispose();
                TelaLogin login = new TelaLogin();
                new LoginController(login);
                login.setVisible(true);

            } catch (Exception ex) {
                view.exibirMensagem("Erro", "Erro ao cadastrar: " + ex.getMessage(), 0);
            }
        });

        this.view.cancelar(e -> {
            view.dispose();
            TelaLogin login = new TelaLogin();
            new LoginController(login);
            login.setVisible(true);
        });
    }
}