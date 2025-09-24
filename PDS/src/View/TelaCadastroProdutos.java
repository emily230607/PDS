package View;

import java.util.ArrayList;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.sql.Connection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import Model.BancoDeDados;
import Model.Produtos;
import Model.ProdutosDAO;

public class TelaCadastroProdutos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField tntNovoNome;
    private JTextField txtNovoPreco;
    private JComboBox<String> comboBoxSelecionarProduto;
    private JComboBox<String> comboBoxSelecionarProdutoEdicao;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaCadastroProdutos frame = new TelaCadastroProdutos();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaCadastroProdutos() {
        setTitle("Cadastrar Produto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 493, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Cadastrar novo Produto");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel.setBounds(155, 11, 182, 25);
        contentPane.add(lblNewLabel);

        JLabel lblRemoverProduto = new JLabel("Remover Produto");
        lblRemoverProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblRemoverProduto.setBounds(167, 132, 136, 25);
        contentPane.add(lblRemoverProduto);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNome.setBounds(10, 52, 56, 25);
        contentPane.add(lblNome);
        
//        class ApenasLetrasFilter extends DocumentFilter {
//            @Override
//            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
//                    throws BadLocationException {
//                if (string.matches("[a-zA-ZÀ-ÿ\\s]+")) {
//                    super.insertString(fb, offset, string, attr);
//                }
//            }
//            @Override
//            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) 
//                    throws BadLocationException {
//                if (string.matches("[a-zA-ZÀ-ÿ\\s]+")) {
//                    super.replace(fb, offset, length, string, attrs);
//                }
//            }
//        }
        
        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNome.setBounds(76, 56, 316, 25);
        contentPane.add(txtNome);
        txtNome.setColumns(10);
        
//        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());

        JLabel lblPreco = new JLabel("Preço");
        lblPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPreco.setBounds(10, 97, 43, 25);
        contentPane.add(lblPreco);        

//        class ApenasNumerosFilter extends DocumentFilter {
//            @Override
//            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
//                    throws BadLocationException {
//                if (string.matches("[0-9,.]+")) {
//                    super.insertString(fb, offset, string, attr);
//                }
//            }
//            @Override
//            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) 
//                    throws BadLocationException {
//                if (string.matches("[0-9,.]+")) {
//                    super.replace(fb, offset, length, string, attrs);
//                }
//            }
//        }
        
        txtPreco = new JTextField();
        txtPreco.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtPreco.setBounds(76, 99, 65, 25);
        contentPane.add(txtPreco);
        
//        ((AbstractDocument) txtPreco.getDocument()).setDocumentFilter(new ApenasNumerosFilter());

        

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnCadastrar.setBounds(298, 98, 94, 23);
        contentPane.add(btnCadastrar);

        JButton btnRemover = new JButton("Remover");
        btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnRemover.setBounds(303, 204, 89, 23);
        contentPane.add(btnRemover);

        JLabel lblSelecionarProduto = new JLabel("Selecionar Produto");
        lblSelecionarProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSelecionarProduto.setBounds(10, 168, 113, 25);
        contentPane.add(lblSelecionarProduto);

        JLabel lblEditarProduto = new JLabel("Editar Produto");
        lblEditarProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEditarProduto.setBounds(189, 227, 114, 25);
        contentPane.add(lblEditarProduto);

        JLabel lblNovoNome = new JLabel("Novo nome");
        lblNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNovoNome.setBounds(10, 309, 73, 25);
        contentPane.add(lblNovoNome);

        JLabel lblNovoPreco = new JLabel("Novo preço");
        lblNovoPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNovoPreco.setBounds(10, 349, 65, 25);
        contentPane.add(lblNovoPreco);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnEditar.setBounds(303, 350, 89, 23);
        contentPane.add(btnEditar);

        JButton btnFinalizar = new JButton("Finalizar");
        btnFinalizar.setBackground(new Color(173, 145, 174));
        btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnFinalizar.setBounds(347, 395, 100, 30);
        contentPane.add(btnFinalizar);

        JLabel lblSelecionarProdutoEdicao = new JLabel("Selecionar Produto");
        lblSelecionarProdutoEdicao.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSelecionarProdutoEdicao.setBounds(10, 267, 113, 25);
        contentPane.add(lblSelecionarProdutoEdicao);

        comboBoxSelecionarProdutoEdicao = new JComboBox<>();
        comboBoxSelecionarProdutoEdicao.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxSelecionarProdutoEdicao.setBounds(123, 270, 269, 25);
        contentPane.add(comboBoxSelecionarProdutoEdicao);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(173, 145, 174));
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancelar.setBounds(28, 395, 100, 30);
        contentPane.add(btnCancelar);

        comboBoxSelecionarProduto = new JComboBox<>();
        comboBoxSelecionarProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxSelecionarProduto.setBounds(136, 170, 256, 23);
        contentPane.add(comboBoxSelecionarProduto);

        tntNovoNome = new JTextField();
        tntNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tntNovoNome.setColumns(10);
        tntNovoNome.setBounds(89, 312, 303, 25);
        contentPane.add(tntNovoNome);
        
//        ((AbstractDocument) tntNovoNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());

        txtNovoPreco = new JTextField();
        txtNovoPreco.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNovoPreco.setBounds(88, 348, 65, 25);
        contentPane.add(txtNovoPreco);
        
//        ((AbstractDocument) txtNovoPreco.getDocument()).setDocumentFilter(new ApenasNumerosFilter());

        // ====== Lógica CRUD ======
        carregarProdutos();

        btnCadastrar.addActionListener(e -> cadastrarProduto());
        btnRemover.addActionListener(e -> removerProduto());
        btnEditar.addActionListener(e -> editarProduto());
    }

    // ---- Métodos auxiliares ----

    private void carregarProdutos() {
        try {
            comboBoxSelecionarProduto.removeAllItems();
            comboBoxSelecionarProdutoEdicao.removeAllItems();

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);

            for (Produtos p : dao.listarTodos()) {
                comboBoxSelecionarProduto.addItem(p.getId() + " - " + p.getNome());
                comboBoxSelecionarProdutoEdicao.addItem(p.getId() + " - " + p.getNome());
            }

            BancoDeDados.desconectar(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void cadastrarProduto() {
        try {
            String nome = txtNome.getText();
            double preco = Double.parseDouble(txtPreco.getText());

            Produtos produto = new Produtos(nome, preco, 10); // estoque inicial fixo 10 (pode mudar)

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            dao.inserir(produto);
            BancoDeDados.desconectar(conn);

            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            carregarProdutos();

            // 🔹 limpa campos
            txtNome.setText("");
            txtPreco.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void removerProduto() {
        try {
            if (comboBoxSelecionarProduto.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para remover!");
                return;
            }

            String item = comboBoxSelecionarProduto.getSelectedItem().toString();
            int id = Integer.parseInt(item.split(" - ")[0]);

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            dao.remover(id);
            BancoDeDados.desconectar(conn);

            JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
            carregarProdutos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover: " + e.getMessage());
        }
    }

    private void editarProduto() {
        try {
            if (comboBoxSelecionarProdutoEdicao.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para editar!");
                return;
            }

            String item = comboBoxSelecionarProdutoEdicao.getSelectedItem().toString();
            int id = Integer.parseInt(item.split(" - ")[0]);

            String novoNome = tntNovoNome.getText();
            double novoPreco = Double.parseDouble(txtNovoPreco.getText().replace(",", "."));

            Produtos produto = new Produtos(id, novoNome, novoPreco, 10);

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            dao.atualizar(produto);
            BancoDeDados.desconectar(conn);

            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            carregarProdutos();

            // 🔹 limpa campos
            tntNovoNome.setText("");
            txtNovoPreco.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + e.getMessage());
        }
    }
}
