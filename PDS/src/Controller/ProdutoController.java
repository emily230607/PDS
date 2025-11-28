package Controller;

import Exception.*;
import Model.*;
import View.TelaCadastroProdutos;
import View.TelaLogin;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class ProdutoController {

    private TelaCadastroProdutos view;
    private ProdutosDAO dao;
    private Connection conn;

    public ProdutoController(TelaCadastroProdutos view) {
        this.view = view;

        try {
            this.conn = BancoDeDados.conectar();
            if (conn == null) {
                throw new BancoDeDadosException("Não foi possível conectar ao banco de dados!");
            }
            
            this.dao = new ProdutosDAO(conn);
            carregarProdutos();
            
        } catch (BancoDeDadosException e) {
            view.mostrarMensagem("Erro de Conexão: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            view.mostrarMensagem("Erro SQL ao conectar: " + e.getMessage());
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
                view.getComboRemover().addItem("Nenhum produto cadastrado");
                view.getComboEditar().addItem("Nenhum produto cadastrado");
                return;
            }
            
            for (Produtos p : lista) {
                String item = p.getId() + " - " + p.getNome();
                view.getComboRemover().addItem(item);
                view.getComboEditar().addItem(item);
            }
            
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao carregar produtos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cadastrarProduto() {
        try {
            String nome = view.getNome().trim();
            String precoStr = view.getPreco().trim();
            String qtdStr = view.getQuantidade().trim();

            validarCamposCadastro(nome, precoStr, qtdStr);
            double preco = validarPreco(precoStr);
            int qtd = validarQuantidade(qtdStr);

            Produtos p = new Produtos(0, nome, preco, qtd);
            dao.inserir(p);
            
            view.mostrarMensagem("Produto cadastrado com sucesso!");
            view.limparCampos();
            carregarProdutos();
            
        } catch (CampoVazioException e) {
            view.mostrarMensagem("Erro: " + e.getMessage());
        } catch (ValorInvalidoException e) {
            view.mostrarMensagem("Erro: " + e.getMessage());
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void removerProduto() {
        try {
            String item = (String) view.getComboRemover().getSelectedItem();
            
            if (item == null || item.isEmpty() || item.equals("Nenhum produto cadastrado")) {
                throw new CampoVazioException("Produto", "Selecione um produto para remover!");
            }

            int resposta = JOptionPane.showConfirmDialog(
                view, 
                "Tem certeza que deseja remover este produto?", 
                "Confirmar Remoção", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (resposta != JOptionPane.YES_OPTION) {
                return;
            }

            int id = Integer.parseInt(item.split(" - ")[0]);
            dao.remover(id);
            
            view.mostrarMensagem("Produto removido com sucesso!");
            carregarProdutos();
            
        } catch (CampoVazioException e) {
            view.mostrarMensagem(e.getMessage());
        } catch (NumberFormatException e) {
            view.mostrarMensagem("Erro ao processar ID!");
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao remover: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editarProduto() {
        try {
            String item = (String) view.getComboEditar().getSelectedItem();
            
            if (item == null || item.isEmpty() || item.equals("Nenhum produto cadastrado")) {
                throw new CampoVazioException("Produto", "Selecione um produto para editar!");
            }

            String nome = view.getNovoNome().trim();
            String precoStr = view.getNovoPreco().trim();
            String qtdStr = view.getNovaQuantidade().trim();

            validarCamposEdicao(nome, precoStr, qtdStr);
            double preco = validarPreco(precoStr);
            int qtd = validarQuantidade(qtdStr);

            int id = Integer.parseInt(item.split(" - ")[0]);
            Produtos p = new Produtos(id, nome, preco, qtd);
            dao.atualizar(p);
            
            view.mostrarMensagem("Produto atualizado com sucesso!");
            view.limparCampos();
            carregarProdutos();
            
        } catch (CampoVazioException e) {
            view.mostrarMensagem("Erro: " + e.getMessage());
        } catch (ValorInvalidoException e) {
            view.mostrarMensagem("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            view.mostrarMensagem("Erro ao processar ID!");
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao atualizar: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void validarCamposCadastro(String nome, String preco, String qtd) throws CampoVazioException {
        if (nome.isEmpty()) {
            throw new CampoVazioException("Nome", "O campo não pode estar vazio.");
        }
        if (preco.isEmpty()) {
            throw new CampoVazioException("Preço", "O campo não pode estar vazio.");
        }
        if (qtd.isEmpty()) {
            throw new CampoVazioException("Quantidade", "O campo não pode estar vazio.");
        }
    }

    private void validarCamposEdicao(String nome, String preco, String qtd) throws CampoVazioException {
        if (nome.isEmpty()) {
            throw new CampoVazioException("Novo Nome", "O campo não pode estar vazio.");
        }
        if (preco.isEmpty()) {
            throw new CampoVazioException("Novo Preço", "O campo não pode estar vazio.");
        }
        if (qtd.isEmpty()) {
            throw new CampoVazioException("Nova Quantidade", "O campo não pode estar vazio.");
        }
    }

    private double validarPreco(String precoStr) throws ValorInvalidoException {
        try {
            double preco = Double.parseDouble(precoStr.replace(",", "."));
            
            if (preco < 0) {
                throw new ValorInvalidoException("Preço", "Não pode ser negativo.");
            }
            if (preco == 0) {
                throw new ValorInvalidoException("Preço", "Deve ser maior que zero.");
            }
            
            return preco;
            
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("Preço", "Formato inválido.");
        }
    }

    private int validarQuantidade(String qtdStr) throws ValorInvalidoException {
        try {
            int qtd = Integer.parseInt(qtdStr);
            
            if (qtd < 0) {
                throw new ValorInvalidoException("Quantidade", "Não pode ser negativa.");
            }
            if (qtd == 0) {
                throw new ValorInvalidoException("Quantidade", "Deve ser maior que zero.");
            }
            
            return qtd;
            
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("Quantidade", "Formato inválido.");
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