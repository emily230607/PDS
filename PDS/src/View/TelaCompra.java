package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.util.ArrayList;

import Model.BancoDeDados;
import Model.Produtos;
import Model.ProdutosDAO;
import Model.Usuarios;

public class TelaCompra extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxProdutos;
    private JTextField txtQuantidade;
    private JTextArea areaCarrinho;
    private JLabel lblTotal;
    private double totalCompra = 0.0;
    private ArrayList<Produtos> carrinho = new ArrayList<>();
    private Usuarios usuario; // quem está logado

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // exemplo de abertura (aqui deveria passar o usuário logado)
                    TelaCompra frame = new TelaCompra(new Usuarios("Cliente Teste", "12345678900", false));
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaCompra(Usuarios usuario) {
        this.usuario = usuario;

        setTitle("Tela de Compra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Compra de Produtos");
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblTitulo.setBounds(200, 10, 200, 30);
        contentPane.add(lblTitulo);

        JLabel lblProduto = new JLabel("Produto:");
        lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProduto.setBounds(30, 60, 80, 25);
        contentPane.add(lblProduto);

        comboBoxProdutos = new JComboBox<>();
        comboBoxProdutos.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxProdutos.setBounds(100, 60, 250, 25);
        contentPane.add(comboBoxProdutos);

        JLabel lblQtd = new JLabel("Qtd:");
        lblQtd.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblQtd.setBounds(370, 60, 40, 25);
        contentPane.add(lblQtd);

        txtQuantidade = new JTextField();
        txtQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtQuantidade.setBounds(410, 60, 50, 25);
        contentPane.add(txtQuantidade);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAdicionar.setBounds(470, 60, 100, 25);
        contentPane.add(btnAdicionar);

        // Área para mostrar os itens do carrinho
        areaCarrinho = new JTextArea();
        areaCarrinho.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(areaCarrinho);
        scroll.setBounds(30, 110, 540, 250);
        contentPane.add(scroll);

        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTotal.setBounds(30, 370, 300, 25);
        contentPane.add(lblTotal);

        JButton btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.setBackground(new Color(173, 145, 174));
        btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnFinalizar.setBounds(370, 370, 200, 30);
        contentPane.add(btnFinalizar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(173, 145, 174));
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancelar.setBounds(30, 410, 120, 30);
        contentPane.add(btnCancelar);

        // ====== Lógica ======
        carregarProdutos();

        btnAdicionar.addActionListener(e -> adicionarAoCarrinho());
        btnFinalizar.addActionListener(e -> finalizarCompra());
        btnCancelar.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
    }

    private void carregarProdutos() {
        try {
            comboBoxProdutos.removeAllItems();
            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);

            for (Produtos p : dao.listarTodos()) {
                comboBoxProdutos.addItem(p.getId() + " - " + p.getNome() + " (R$" + p.getPreco() + ")");
            }

            BancoDeDados.desconectar(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void adicionarAoCarrinho() {
        if (comboBoxProdutos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }

        try {
            String item = comboBoxProdutos.getSelectedItem().toString();
            int id = Integer.parseInt(item.split(" - ")[0]);
            int qtd = Integer.parseInt(txtQuantidade.getText());

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            Produtos produto = dao.buscarPorId(id);
            BancoDeDados.desconectar(conn);

            if (produto == null) {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!");
                return;
            }

            if (qtd > produto.getQuantidade()) {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente!");
                return;
            }

            double subtotal = produto.getPreco() * qtd;
            totalCompra += subtotal;

            carrinho.add(new Produtos(produto.getId(), produto.getNome(), produto.getPreco(), qtd));

            areaCarrinho.append(produto.getNome() + " x" + qtd + " - R$ " + subtotal + "\n");
            lblTotal.setText("Total: R$ " + String.format("%.2f", totalCompra));

            txtQuantidade.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + e.getMessage());
        }
    }

    private void finalizarCompra() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carrinho vazio!");
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

            // Emitir "nota fiscal"
            StringBuilder nota = new StringBuilder();
            nota.append("Nota Fiscal\n");
            nota.append("Cliente: " + usuario.getNome() + " - CPF: " + usuario.getCpf() + "\n\n");
            for (Produtos p : carrinho) {
                double subtotal = p.getPreco() * p.getQuantidade();
                nota.append(p.getNome() + " x" + p.getQuantidade() + " - R$ " + subtotal + "\n");
            }
            nota.append("\nTotal: R$ " + String.format("%.2f", totalCompra));

            JOptionPane.showMessageDialog(this, nota.toString());

            carrinho.clear();
            areaCarrinho.setText("");
            totalCompra = 0;
            lblTotal.setText("Total: R$ 0,00");

            carregarProdutos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao finalizar compra: " + e.getMessage());
        }
    }
}
