package Controller;

import Model.*;
import View.TelaCadastroProdutos;
import View.TelaLogin;
import java.sql.Connection;
import java.sql.SQLException;
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
                view.mostrarMensagem("ERRO CRÍTICO: Não foi possível conectar ao banco de dados!\n" +
                    "Verifique se o MySQL está rodando.");
            }
            
        } catch (SQLException e) {
            view.mostrarMensagem("Erro de conexão SQL: " + e.getMessage());
            e.printStackTrace();
            
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado ao iniciar: " + e.getMessage());
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
            
            if (lista.isEmpty()) {
                System.out.println("Nenhum produto cadastrado ainda");
            }
            
            for (Produtos p : lista) {
                String item = p.getId() + " - " + p.getNome();
                view.getComboRemover().addItem(item);
                view.getComboEditar().addItem(item);
            }
            
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao carregar produtos do banco: " + e.getMessage());
            e.printStackTrace();
            
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado ao carregar produtos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cadastrarProduto() {
        try {
            String nome = view.getNome();
            String precoStr = view.getPreco();
            String qtdStr = view.getQuantidade();

            if (nome == null || nome.trim().isEmpty()) {
                view.mostrarMensagem("Nome do produto não pode ser vazio!");
                return;
            }

            if (precoStr == null || precoStr.trim().isEmpty()) {
                view.mostrarMensagem("Preço não pode ser vazio!");
                return;
            }

            if (qtdStr == null || qtdStr.trim().isEmpty()) {
                view.mostrarMensagem("Quantidade não pode ser vazia!");
                return;
            }

            double preco = Double.parseDouble(precoStr.replace(",", "."));
            int qtd = Integer.parseInt(qtdStr);

            if (preco <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero!");
            }

            if (qtd < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa!");
            }

            Produtos p = new Produtos(0, nome, preco, qtd);
            dao.inserir(p);
            
            view.mostrarMensagem("Produto cadastrado com sucesso!");
            view.limparCampos();
            carregarProdutos();
            
        } catch (NumberFormatException e) {
            view.mostrarMensagem("ERRO: Preço ou quantidade inválidos!\n" +
                "Use números válidos (ex: 10.50 para preço)");
            
        } catch (IllegalArgumentException e) {
            view.mostrarMensagem("Erro de validação: " + e.getMessage());
            
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao cadastrar no banco: " + e.getMessage());
            e.printStackTrace();
            
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void removerProduto() {
        try {
            String item = (String) view.getComboRemover().getSelectedItem();
            
            if (item == null || item.trim().isEmpty()) {
                view.mostrarMensagem("Selecione um produto para remover!");
                return;
            }

            int id = Integer.parseInt(item.split(" - ")[0]);
            
            dao.remover(id);
            view.mostrarMensagem("Produto removido com sucesso!");
            carregarProdutos();
            
        } catch (NumberFormatException e) {
            view.mostrarMensagem("Erro ao processar ID do produto!");
            
        } catch (IllegalArgumentException e) {
            view.mostrarMensagem("Erro de validação: " + e.getMessage());
            
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao remover do banco: " + e.getMessage());
            e.printStackTrace();
            
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado ao remover: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editarProduto() {
        try {
            String item = (String) view.getComboEditar().getSelectedItem();
            
            if (item == null || item.trim().isEmpty()) {
                view.mostrarMensagem("Selecione um produto para editar!");
                return;
            }

            String nome = view.getNovoNome();
            String precoStr = view.getNovoPreco();
            String qtdStr = view.getNovaQuantidade();

            if (nome == null || nome.trim().isEmpty()) {
                view.mostrarMensagem("Novo nome não pode ser vazio!");
                return;
            }

            if (precoStr == null || precoStr.trim().isEmpty()) {
                view.mostrarMensagem("Novo preço não pode ser vazio!");
                return;
            }

            if (qtdStr == null || qtdStr.trim().isEmpty()) {
                view.mostrarMensagem("Nova quantidade não pode ser vazia!");
                return;
            }

            int id = Integer.parseInt(item.split(" - ")[0]);
            double preco = Double.parseDouble(precoStr.replace(",", "."));
            int qtd = Integer.parseInt(qtdStr);

            if (preco <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero!");
            }

            if (qtd < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa!");
            }

            Produtos p = new Produtos(id, nome, preco, qtd);
            dao.atualizar(p);
            
            view.mostrarMensagem(" Produto atualizado com sucesso!");
            view.limparCampos();
            carregarProdutos();
            
        } catch (NumberFormatException e) {
            view.mostrarMensagem("ERRO: Valores numéricos inválidos!\n" +
                "Use números válidos para preço e quantidade.");
            
        } catch (IllegalArgumentException e) {
            view.mostrarMensagem("Erro de validação: " + e.getMessage());
            
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao atualizar no banco: " + e.getMessage());
            e.printStackTrace();
            
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado ao editar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void voltarLogin() {
        try {
        	
            if (conn != null) {
                BancoDeDados.desconectar(conn);
            }
            
            view.dispose();
            
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
            
        } catch (Exception e) {
            System.err.println("Erro ao voltar para login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}