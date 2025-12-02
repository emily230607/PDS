package Controller;

import java.sql.Connection;
import java.sql.SQLException;
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
        Connection conn = null;
        
        try {
            JComboBox<String> combo = view.getComboBoxProdutos();
            combo.removeAllItems();
            
            conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);

            for (Produtos p : dao.listarTodos()) {
                combo.addItem(p.getId() + " - " + p.getNome() + 
                    " (R$" + String.format("%.2f", p.getPreco()) + ")");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "Erro SQL ao carregar produtos: " + e.getMessage(),
                "Erro de Banco",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Erro inesperado ao carregar produtos: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        } finally {
            BancoDeDados.desconectar(conn);
        }
    }

    public void adicionarAoCarrinho() {
        Connection conn = null;
        
        try {
            JComboBox<String> combo = view.getComboBoxProdutos();
            JTextField txtQtd = view.getTxtQuantidade();
            JTextArea area = view.getAreaCarrinho();
            JLabel lblTotal = view.getLblTotal();

            if (combo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(view, 
                    "Selecione um produto!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txtQtd.getText() == null || txtQtd.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Informe a quantidade!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String item = combo.getSelectedItem().toString();
            int id = Integer.parseInt(item.split(" - ")[0]);
            int qtd = Integer.parseInt(txtQtd.getText());

            if (qtd <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero!");
            }

            conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            Produtos produto = dao.buscarPorId(id);

            if (produto == null) {
                throw new RuntimeException("Produto não encontrado no banco!");
            }

            if (qtd > produto.getQuantidade()) {
                JOptionPane.showMessageDialog(view, 
                    "Estoque insuficiente!\n" +
                    "Disponível: " + produto.getQuantidade() + " unidades",
                    "Erro de Estoque",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            double subtotal = produto.getPreco() * qtd;
            totalCompra += subtotal;

            carrinho.add(new Produtos(produto.getId(), produto.getNome(), 
                produto.getPreco(), qtd));

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            area.append(produto.getNome() + " x" + qtd + " - " + 
                nf.format(subtotal) + "\n");
            lblTotal.setText("Total: " + nf.format(totalCompra));

            txtQtd.setText("");
            
            System.out.println("Produto adicionado ao carrinho!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Quantidade inválida!\nUse apenas números inteiros.",
                "Erro de Formato",
                JOptionPane.ERROR_MESSAGE);
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, 
                "Erro de validação: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "Erro ao consultar banco: " + e.getMessage(),
                "Erro SQL",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Erro inesperado ao adicionar: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        } finally {
            BancoDeDados.desconectar(conn);
        }
    }
    
    public void finalizarCompra() {
        Connection conn = null;
        
        try {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Carrinho vazio!\nAdicione produtos antes de finalizar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            conn = BancoDeDados.conectar();
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
                ItensCompra item = new ItensCompra(compraId, p.getId(), 
                    p.getQuantidade(), subtotal);
                itensDao.inserir(item);
            }

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

            JOptionPane.showMessageDialog(view, nota.toString(), 
                "Compra Finalizada com Sucesso!", 
                JOptionPane.INFORMATION_MESSAGE);

            carrinho.clear();
            view.getAreaCarrinho().setText("");
            totalCompra = 0;
            view.getLblTotal().setText("Total: R$ 0,00");
            carregarProdutos();
            
            System.out.println("✅ Compra finalizada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "Erro ao finalizar compra no banco:\n" + e.getMessage(),
                "Erro SQL",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Erro inesperado ao finalizar compra:\n" + e.getMessage(),
                "Erro Crítico",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        } finally {
            BancoDeDados.desconectar(conn);
        }
    }
    
    public void cancelar() {
        try {
            view.dispose();

            TelaLogin login = new TelaLogin();
            login.setVisible(true);
            
        } catch (Exception e) {
            System.err.println("Erro ao cancelar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}