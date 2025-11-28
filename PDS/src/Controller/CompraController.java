package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import Exception.*;
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
            
            if (conn == null) {
                throw new BancoDeDadosException("Não foi possível conectar ao banco.");
            }
            
            ProdutosDAO dao = new ProdutosDAO(conn);
            List<Produtos> produtos = dao.listarTodos();
            
            if (produtos.isEmpty()) {
                combo.addItem("Nenhum produto disponível");
                JOptionPane.showMessageDialog(view, 
                    "Não há produtos disponíveis.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (Produtos p : produtos) {
                if (p.getQuantidade() > 0) {
                    combo.addItem(p.getId() + " - " + p.getNome() + 
                                " (R$ " + String.format("%.2f", p.getPreco()) + 
                                ") - Estoque: " + p.getQuantidade());
                }
            }
            
        } catch (BancoDeDadosException e) {
            JOptionPane.showMessageDialog(view, 
                "Erro: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "Erro ao carregar: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Erro inesperado: " + e.getMessage(), 
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
                throw new CampoVazioException("Produto", "Selecione um produto!");
            }
            
            String itemSelecionado = combo.getSelectedItem().toString();
            if (itemSelecionado.equals("Nenhum produto disponível")) {
                throw new CampoVazioException("Produto", "Não há produtos!");
            }

            if (txtQtd.getText().trim().isEmpty()) {
                throw new CampoVazioException("Quantidade", "Informe a quantidade!");
            }

            int qtd;
            try {
                qtd = Integer.parseInt(txtQtd.getText().trim());
            } catch (NumberFormatException e) {
                throw new ValorInvalidoException("Quantidade", "Deve ser um número.");
            }

            if (qtd <= 0) {
                throw new ValorInvalidoException("Quantidade", "Deve ser maior que zero.");
            }

            int id = Integer.parseInt(itemSelecionado.split(" - ")[0]);
            
            conn = BancoDeDados.conectar();
            if (conn == null) {
                throw new BancoDeDadosException("Não foi possível conectar.");
            }
            
            ProdutosDAO dao = new ProdutosDAO(conn);
            Produtos produto = dao.buscarPorId(id);

            if (produto == null) {
                throw new BancoDeDadosException("Produto não encontrado!");
            }

            if (qtd > produto.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                    produto.getNome(), 
                    produto.getQuantidade(), 
                    qtd
                );
            }

            double subtotal = produto.getPreco() * qtd;
            totalCompra += subtotal;

            carrinho.add(new Produtos(produto.getId(), produto.getNome(), produto.getPreco(), qtd));

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            area.append(String.format("%s x%d - %s%n", 
                produto.getNome(), 
                qtd, 
                nf.format(subtotal)));
            lblTotal.setText("Total: " + nf.format(totalCompra));

            txtQtd.setText("");
            
            JOptionPane.showMessageDialog(view, 
                "Produto adicionado!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (CampoVazioException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Campo Vazio", JOptionPane.WARNING_MESSAGE);
        } catch (ValorInvalidoException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Valor Inválido", JOptionPane.ERROR_MESSAGE);
        } catch (EstoqueInsuficienteException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Estoque Insuficiente", JOptionPane.WARNING_MESSAGE);
        } catch (BancoDeDadosException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "Erro SQL: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Erro: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            BancoDeDados.desconectar(conn);
        }
    }

    public void finalizarCompra() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Carrinho vazio!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        
        try {
            conn = BancoDeDados.conectar();
            
            if (conn == null) {
                throw new BancoDeDadosException("Não foi possível conectar.");
            }
            
            conn.setAutoCommit(false);
            
            ProdutosDAO prodDao = new ProdutosDAO(conn);
            ComprasDAO compraDao = new ComprasDAO(conn);
            ItensCompraDAO itensDao = new ItensCompraDAO(conn);

            Compras compra = new Compras(usuario.getId(), totalCompra);
            int compraId = compraDao.inserir(compra);

            if (compraId == -1) {
                throw new BancoDeDadosException("Erro ao registrar compra.");
            }

            for (Produtos p : carrinho) {
                Produtos prodBanco = prodDao.buscarPorId(p.getId());
                
                if (prodBanco == null) {
                    throw new BancoDeDadosException("Produto não encontrado!");
                }
                
                if (p.getQuantidade() > prodBanco.getQuantidade()) {
                    throw new EstoqueInsuficienteException(
                        prodBanco.getNome(), 
                        prodBanco.getQuantidade(), 
                        p.getQuantidade()
                    );
                }
                
                int novoEstoque = prodBanco.getQuantidade() - p.getQuantidade();
                prodBanco.setQuantidade(novoEstoque);
                prodDao.atualizar(prodBanco);

                double subtotal = p.getPreco() * p.getQuantidade();
                ItensCompra item = new ItensCompra(compraId, p.getId(), p.getQuantidade(), subtotal);
                itensDao.inserir(item);
            }
            
            conn.commit();

            gerarNotaFiscal();

            carrinho.clear();
            view.getAreaCarrinho().setText("");
            totalCompra = 0;
            view.getLblTotal().setText("Total: R$ 0,00");
            carregarProdutos();

        } catch (EstoqueInsuficienteException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(view, 
                "Compra cancelada!\n" + e.getMessage(), 
                "Estoque Insuficiente", 
                JOptionPane.ERROR_MESSAGE);
        } catch (BancoDeDadosException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(view, 
                "Erro: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(view, 
                "Erro SQL: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(view, 
                "Erro: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            BancoDeDados.desconectar(conn);
        }
    }

    private void gerarNotaFiscal() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        StringBuilder nota = new StringBuilder();
        
        nota.append("========== NOTA FISCAL ==========\n\n");
        nota.append("Cliente: ").append(usuario.getNome()).append("\n");
        nota.append("CPF: ").append(usuario.getCpf()).append("\n\n");
        nota.append("Produtos:\n");
        nota.append("-".repeat(33)).append("\n");

        for (Produtos p : carrinho) {
            double subtotal = p.getPreco() * p.getQuantidade();
            nota.append(String.format("%-20s x%-2d %10s%n", 
                p.getNome(), 
                p.getQuantidade(), 
                nf.format(subtotal)));
        }
        
        nota.append("-".repeat(33)).append("\n");
        nota.append(String.format("%-23s %10s%n", "TOTAL:", nf.format(totalCompra)));
        nota.append("=".repeat(33)).append("\n\n");
        nota.append("Obrigado pela preferência!");

        JOptionPane.showMessageDialog(view, 
            nota.toString(), 
            "Compra Finalizada!", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void cancelar() {
        if (!carrinho.isEmpty()) {
            int resposta = JOptionPane.showConfirmDialog(view, 
                "Você tem itens no carrinho. Deseja sair?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION);
            
            if (resposta != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        view.dispose();
        TelaLogin login = new TelaLogin();
        new LoginController(login);
        login.setVisible(true);
    }
}