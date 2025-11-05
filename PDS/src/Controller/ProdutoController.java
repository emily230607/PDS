package Controller;

import Model.*;
import View.TelaCadastroProdutos;
import java.sql.Connection;
import java.util.List;

public class ProdutoController {

    private TelaCadastroProdutos view;
    private ProdutosDAO dao;

    public ProdutoController(TelaCadastroProdutos view) {
        this.view = view;

        try {
            Connection conn = BancoDeDados.conectar();
            this.dao = new ProdutosDAO(conn);
        } catch (Exception e) {
            view.mostrarMensagem("Erro de conexão com o banco: " + e.getMessage());
        }

        carregarProdutos();

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
        }
    }

    private void cadastrarProduto() {
        try {
            String nome = view.getNome();
            double preco = Double.parseDouble(view.getPreco().replace(",", "."));
            int qtd = Integer.parseInt(view.getQuantidade());

            Produtos p = new Produtos(0, nome, preco, qtd);
            dao.inserir(p);
            view.mostrarMensagem("Produto cadastrado com sucesso!");
            view.limparCampos();
            carregarProdutos();
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void removerProduto() {
        try {
            String item = (String) view.getComboRemover().getSelectedItem();
            if (item == null) {
                view.mostrarMensagem("Selecione um produto para remover!");
                return;
            }

            int id = Integer.parseInt(item.split(" - ")[0]);
            dao.remover(id);
            view.mostrarMensagem("Produto removido com sucesso!");
            carregarProdutos();
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao remover: " + e.getMessage());
        }
    }

    private void editarProduto() {
        try {
            String item = (String) view.getComboEditar().getSelectedItem();
            if (item == null) {
                view.mostrarMensagem("Selecione um produto para editar!");
                return;
            }

            int id = Integer.parseInt(item.split(" - ")[0]);
            String nome = view.getNovoNome();
            double preco = Double.parseDouble(view.getNovoPreco().replace(",", "."));
            int qtd = Integer.parseInt(view.getNovaQuantidade());

            Produtos p = new Produtos(id, nome, preco, qtd);
            dao.atualizar(p);
            view.mostrarMensagem("Produto atualizado com sucesso!");
            view.limparCampos();
            carregarProdutos();
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao editar: " + e.getMessage());
        }
    }

    private void voltarLogin() {
        view.dispose();
        new View.TelaLogin().setVisible(true);
    }
}
