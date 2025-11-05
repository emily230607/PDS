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
                combo.addItem(p.getId() + " - " + p.getNome() + " (R$" + p.getPreco() + ")");
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

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            Produtos produto = dao.buscarPorId(id);
            BancoDeDados.desconectar(conn);

            if (produto == null) {
                JOptionPane.showMessageDialog(view, "Produto não encontrado!");
                return;
            }

            if (qtd > produto.getQuantidade()) {
                JOptionPane.showMessageDialog(view, "Estoque insuficiente!");
                return;
            }

            double subtotal = produto.getPreco() * qtd;
            totalCompra += subtotal;

            carrinho.add(new Produtos(produto.getId(), produto.getNome(), produto.getPreco(), qtd));

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            area.append(produto.getNome() + " x" + qtd + " - " + nf.format(subtotal) + "\n");
            lblTotal.setText("Total: " + nf.format(totalCompra));

            txtQtd.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao adicionar: " + e.getMessage());
        }
    }

    public void finalizarCompra() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Carrinho vazio!");
            return;
        }

        try {
            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);

            for (Produtos p : carrinho) {
                Produtos prodBanco = dao.buscarPorId(p.getId());
                int novoEstoque = prodBanco.getQuantidade() - p.getQuantidade();
                prodBanco.setQuantidade(novoEstoque);
                dao.atualizar(prodBanco);
            }

            BancoDeDados.desconectar(conn);

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            StringBuilder nota = new StringBuilder();
            nota.append("Nota Fiscal\n");
            nota.append("Cliente: " + usuario.getNome() + " - CPF: " + usuario.getCpf() + "\n\n");

            for (Produtos p : carrinho) {
                double subtotal = p.getPreco() * p.getQuantidade();
                nota.append(p.getNome() + " x" + p.getQuantidade() + " - " + nf.format(subtotal) + "\n");
            }
            nota.append("\nTotal: " + nf.format(totalCompra));

            JOptionPane.showMessageDialog(view, nota.toString(), "Compra Finalizada", JOptionPane.INFORMATION_MESSAGE);

            carrinho.clear();
            view.getAreaCarrinho().setText("");
            totalCompra = 0;
            view.getLblTotal().setText("Total: R$ 0,00");
            carregarProdutos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao finalizar compra: " + e.getMessage());
        }
    }

    public void cancelar() {
        view.dispose();
        new TelaLogin().setVisible(true);
    }
}
