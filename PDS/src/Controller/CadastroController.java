package Controller;

import java.sql.Connection;
import Model.BancoDeDados;
import Model.Usuarios;
import Model.UsuariosDAO;
import View.TelaCadastro;
import View.TelaLogin;

/**
 * Classe responsável pela comunicação entre a view (TelaCadastro) e o model (UsuariosDAO).
 */
public class CadastroController {
    private final TelaCadastro view;
    private final UsuariosDAO model;

    public CadastroController(TelaCadastro view, UsuariosDAO model) {
        this.view = view;
        this.model = model;

        // Botão "Cadastrar"
        this.view.cadastrar(e -> {
            String nome = view.getNome();
            String cpf = view.getCPF();
            boolean isAdmin = view.isAdmin();

            if (nome.isEmpty() || cpf.contains("_")) {
                view.exibirMensagem("Erro", "Preencha todos os campos corretamente!", 0);
                return;
            }

            try {
                Connection conn = BancoDeDados.conectar();

                Usuarios usuario = new Usuarios(nome, cpf, isAdmin);
                model.adicionarUsuario(usuario);

                view.exibirMensagem("Sucesso", "Usuário cadastrado com sucesso!", 1);
                view.limparCampos();

                BancoDeDados.desconectar(conn);

                view.dispose();
                new TelaLogin().setVisible(true);

            } catch (Exception ex) {
                view.exibirMensagem("Erro", "Erro ao cadastrar: " + ex.getMessage(), 0);
            }
        });

        // Botão "Cancelar"
        this.view.cancelar(e -> {
            view.dispose();
            new TelaLogin().setVisible(true);
        });
    }
}
