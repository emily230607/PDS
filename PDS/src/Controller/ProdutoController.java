package Controller;

import Model.*;
import View.TelaCadastroProdutos;
import View.TelaLogin;
import java.sql.Connection;
import java.util.List;

public class ProdutoController {

    private TelaCadastroProdutos view;
    private ProdutosDAO dao;
    private Connection conn;

    public ProdutoController(TelaCadastroProdutos view) {
        this.view = view;

        try {
            this.conn = BancoDeDados.conectar();
            if (conn != null) {
                this.dao = new ProdutosDAO(conn);
                carregarProdutos();
            } else {
                view.mostrarMensagem("Erro: Não foi possível conectar ao banco de dados!");
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro de conexão com o banco: " + e.getMessage());
            e.printStackTrace();
        }

        view.addCadastrarListener(e -> cadastrarProduto());
        view.addRemoverListener(e -> removerProduto());
        view.addEditarListener(e -> editarProduto());
        view.addCancelarListener(e -> voltarLogin());
        view.addFinalizarListener(e -> voltarLogin());
    }

    private void carregarProdutos() {
        try {
            view.getComboRemover().removeAllItems();
            view.getComboEditar().removeAllItems();

            List<Produtos> lista = dao.listarTodos();
            for (Produtos p : lista) {
                String item = p.getId() + " - " + p.getNome();
                view.getComboRemover().addItem(item);
                view.getComboEditar().addItem(item);
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao carregar produtos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cadastrarProduto() {
        try {
            String nome = view.getNome();
            String precoStr = view.getPreco();
            String qtdStr = view.getQuantidade();

            if (nome.isEmpty() || precoStr.isEmpty() || qtdStr.isEmpty()) {
                view.mostrarMensagem("Preencha todos os campos!");
                return;
            }

            double preco = Double.parseDouble(precoStr.replace(",", "."));
            int qtd = Integer.parseInt(qtdStr);

            Produtos p = new Produtos(0, nome, preco, qtd);
            dao.inserir(p);
            view.mostrarMensagem("Produto cadastrado com sucesso!");
            view.limparCampos();
            carregarProdutos();
        } catch (NumberFormatException e) {
            view.mostrarMensagem("Erro: Preço ou quantidade inválidos!");
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void removerProduto() {
        try {
            String item = (String) view.getComboRemover().getSelectedItem();
            if (item == null || item.isEmpty()) {
                view.mostrarMensagem("Selecione um produto para remover!");
                return;
            }

            int id = Integer.parseInt(item.split(" - ")[0]);
            dao.remover(id);
            view.mostrarMensagem("Produto removido com sucesso!");
            carregarProdutos();
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao remover: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editarProduto() {
        try {
            String item = (String) view.getComboEditar().getSelectedItem();
            if (item == null || item.isEmpty()) {
                view.mostrarMensagem("Selecione um produto para editar!");
                return;
            }

            String nome = view.getNovoNome();
            String precoStr = view.getNovoPreco();
            String qtdStr = view.getNovaQuantidade();

            if (nome.isEmpty() || precoStr.isEmpty() || qtdStr.isEmpty()) {
                view.mostrarMensagem("Preencha todos os campos de edição!");
                return;
            }

            int id = Integer.parseInt(item.split(" - ")[0]);
            double preco = Double.parseDouble(precoStr.replace(",", "."));
            int qtd = Integer.parseInt(qtdStr);

            Produtos p = new Produtos(id, nome, preco, qtd);
            dao.atualizar(p);
            view.mostrarMensagem("Produto atualizado com sucesso!");
            view.limparCampos();
            carregarProdutos();
        } catch (NumberFormatException e) {
            view.mostrarMensagem("Erro: Preço ou quantidade inválidos!");
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao editar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void voltarLogin() {
        if (conn != null) {
            BancoDeDados.desconectar(conn);
        }
        view.dispose();
        TelaLogin login = new TelaLogin();
        new LoginController(login);
        login.setVisible(true);
    }
}