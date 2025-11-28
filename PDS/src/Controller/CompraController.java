package Controller;

import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import Model.*;
import View.*;

public class CompraController {

    private TelaCompra view;
    private Usuarios usuario;
    private ArrayList<Produtos> carrinho = new ArrayList<>();
    private double totalCompra = 0.0;

    public CompraController(TelaCompra view, Usuarios usuario) {
        this.view = view;
        this.usuario = usuario;
    }

    public void carregarProdutos() {
        try {
            JComboBox<String> combo = view.getComboBoxProdutos();
            combo.removeAllItems();
            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);

            for (Produtos p : dao.listarTodos()) {
                combo.addItem(p.getId() + " - " + p.getNome() + " (R$" + String.format("%.2f", p.getPreco()) + ")");
            }

            BancoDeDados.desconectar(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    public void adicionarAoCarrinho() {
        JComboBox<String> combo = view.getComboBoxProdutos();
        JTextField txtQtd = view.getTxtQuantidade();
        JTextArea area = view.getAreaCarrinho();
        JLabel lblTotal = view.getLblTotal();

        if (combo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Selecione um produto!");
            return;
        }

        if (txtQtd.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Informe a quantidade!");
            return;
        }

        try {
            String item = combo.getSelectedItem().toString();
            int id = Integer.parseInt(item.split(" - ")[0]);
            int qtd = Integer.parseInt(txtQtd.getText());

            if (qtd <= 0) {
                JOptionPane.showMessageDialog(view, "Quantidade deve ser maior que zero!");
                return;
            }

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            Produtos produto = dao.buscarPorId(id);
            BancoDeDados.desconectar(conn);

            if (produto == null) {
                JOptionPane.showMessageDialog(view, "Produto não encontrado!");
                return;
            }

            if (qtd > produto.getQuantidade()) {
                JOptionPane.showMessageDialog(view, "Estoque insuficiente! Disponível: " + produto.getQuantidade());
                return;
            }

            double subtotal = produto.getPreco() * qtd;
            totalCompra += subtotal;

            carrinho.add(new Produtos(produto.getId(), produto.getNome(), produto.getPreco(), qtd));

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            area.append(produto.getNome() + " x" + qtd + " - " + nf.format(subtotal) + "\n");
            lblTotal.setText("Total: " + nf.format(totalCompra));

            txtQtd.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Quantidade inválida!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao adicionar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void finalizarCompra() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Carrinho vazio!");
            return;
        }

        try {
            Connection conn = BancoDeDados.conectar();
            ProdutosDAO prodDao = new ProdutosDAO(conn);
            ComprasDAO compraDao = new ComprasDAO(conn);
            ItensCompraDAO itensDao = new ItensCompraDAO(conn);

            Compras compra = new Compras(usuario.getId(), totalCompra);
            int compraId = compraDao.inserir(compra);

            for (Produtos p : carrinho) {
                Produtos prodBanco = prodDao.buscarPorId(p.getId());
                int novoEstoque = prodBanco.getQuantidade() - p.getQuantidade();
                prodBanco.setQuantidade(novoEstoque);
                prodDao.atualizar(prodBanco);

                double subtotal = p.getPreco() * p.getQuantidade();
                ItensCompra item = new ItensCompra(compraId, p.getId(), p.getQuantidade(), subtotal);
                itensDao.inserir(item);
            }

            BancoDeDados.desconectar(conn);

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            StringBuilder nota = new StringBuilder();
            nota.append("========== NOTA FISCAL ==========\n\n");
            nota.append("Cliente: ").append(usuario.getNome()).append("\n");
            nota.append("CPF: ").append(usuario.getCpf()).append("\n\n");
            nota.append("Produtos:\n");

            for (Produtos p : carrinho) {
                double subtotal = p.getPreco() * p.getQuantidade();
                nota.append(String.format("%s x%d - %s\n", 
                    p.getNome(), p.getQuantidade(), nf.format(subtotal)));
            }
            
            nota.append("\n").append("=".repeat(33)).append("\n");
            nota.append(String.format("TOTAL: %s\n", nf.format(totalCompra)));
            nota.append("=".repeat(33));

            JOptionPane.showMessageDialog(view, nota.toString(), "Compra Finalizada", JOptionPane.INFORMATION_MESSAGE);

            carrinho.clear();
            view.getAreaCarrinho().setText("");
            totalCompra = 0;
            view.getLblTotal().setText("Total: R$ 0,00");
            carregarProdutos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao finalizar compra: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cancelar() {
        view.dispose();
        TelaLogin login = new TelaLogin();
        new LoginController(login);
        login.setVisible(true);
    }
}