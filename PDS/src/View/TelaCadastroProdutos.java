package View;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import Model.BancoDeDados;
import Model.Produtos;
import Model.ProdutosDAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroProdutos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome, txtPreco, txtQuantidade;
    private JTextField txtNovoNome, txtNovoPreco, txtNovaQuantidade;
    private JComboBox<String> comboBoxSelecionarProduto, comboBoxSelecionarProdutoEdicao;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaCadastroProdutos frame = new TelaCadastroProdutos();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaCadastroProdutos() {
        setTitle("Cadastrar Produto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 550);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Cadastrar novo Produto");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel.setBounds(200, 10, 200, 25);
        contentPane.add(lblNewLabel);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNome.setBounds(10, 52, 56, 25);
        contentPane.add(lblNome);

        // --- Filtros de entrada ---
        class ApenasLetrasFilter extends DocumentFilter {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
                    throws BadLocationException {
                if (string.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }
        }

        class ApenasNumerosFilter extends DocumentFilter {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("[0-9,.]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
                    throws BadLocationException {
                if (string.matches("[0-9,.]+")) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }
        }

        // --- Campos de cadastro ---
        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNome.setBounds(76, 56, 316, 25);
        contentPane.add(txtNome);
        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());

        JLabel lblPreco = new JLabel("Preço");
        lblPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPreco.setBounds(10, 97, 43, 25);
        contentPane.add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtPreco.setBounds(76, 99, 65, 25);
        contentPane.add(txtPreco);
        ((AbstractDocument) txtPreco.getDocument()).setDocumentFilter(new ApenasNumerosFilter());

        JLabel lblQuantidade = new JLabel("Quantidade");
        lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblQuantidade.setBounds(160, 97, 80, 25);
        contentPane.add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtQuantidade.setBounds(240, 99, 50, 25);
        contentPane.add(txtQuantidade);
        ((AbstractDocument) txtQuantidade.getDocument()).setDocumentFilter(new ApenasNumerosFilter());

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnCadastrar.setBounds(320, 98, 120, 25);
        contentPane.add(btnCadastrar);

        // --- Remover produto ---
        JLabel lblRemoverProduto = new JLabel("Remover Produto");
        lblRemoverProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblRemoverProduto.setBounds(230, 140, 150, 25);
        contentPane.add(lblRemoverProduto);

        comboBoxSelecionarProduto = new JComboBox<>();
        comboBoxSelecionarProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxSelecionarProduto.setBounds(139, 176, 300, 25);
        contentPane.add(comboBoxSelecionarProduto);

        JButton btnRemover = new JButton("Remover");
        btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnRemover.setBounds(450, 180, 100, 25);
        contentPane.add(btnRemover);

        // --- Editar produto ---
        JLabel lblEditarProduto = new JLabel("Editar Produto");
        lblEditarProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEditarProduto.setBounds(240, 230, 150, 25);
        contentPane.add(lblEditarProduto);

        comboBoxSelecionarProdutoEdicao = new JComboBox<>();
        comboBoxSelecionarProdutoEdicao.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxSelecionarProdutoEdicao.setBounds(139, 266, 300, 25);
        contentPane.add(comboBoxSelecionarProdutoEdicao);

        JLabel lblNovoNome = new JLabel("Novo nome");
        lblNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNovoNome.setBounds(10, 320, 80, 25);
        contentPane.add(lblNovoNome);

        txtNovoNome = new JTextField();
        txtNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNovoNome.setBounds(100, 320, 350, 25);
        contentPane.add(txtNovoNome);
        ((AbstractDocument) txtNovoNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());

        JLabel lblNovoPreco = new JLabel("Novo preço");
        lblNovoPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNovoPreco.setBounds(10, 360, 80, 25);
        contentPane.add(lblNovoPreco);

        txtNovoPreco = new JTextField();
        txtNovoPreco.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNovoPreco.setBounds(100, 360, 100, 25);
        contentPane.add(txtNovoPreco);
        ((AbstractDocument) txtNovoPreco.getDocument()).setDocumentFilter(new ApenasNumerosFilter());

        JLabel lblNovaQtd = new JLabel("Nova qtd");
        lblNovaQtd.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNovaQtd.setBounds(220, 360, 80, 25);
        contentPane.add(lblNovaQtd);

        txtNovaQuantidade = new JTextField();
        txtNovaQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNovaQuantidade.setBounds(300, 360, 80, 25);
        contentPane.add(txtNovaQuantidade);
        ((AbstractDocument) txtNovaQuantidade.getDocument()).setDocumentFilter(new ApenasNumerosFilter());

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnEditar.setBounds(420, 360, 100, 25);
        contentPane.add(btnEditar);

        // --- Botões inferiores ---
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(173, 145, 174));
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancelar.setBounds(30, 450, 120, 30);
        contentPane.add(btnCancelar);

        btnCancelar.addActionListener(e -> {
		    dispose(); // Fecha a tela atual
		    new TelaLogin().setVisible(true); // Vai para TelaCadastro
		});

        JButton btnFinalizar = new JButton("Finalizar");
        btnFinalizar.setBackground(new Color(173, 145, 174));
        btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnFinalizar.setBounds(430, 450, 120, 30);
        contentPane.add(btnFinalizar);
        
        btnFinalizar.addActionListener(e -> {
		    dispose(); // Fecha a tela atual
		    new TelaLogin().setVisible(true); // Vai para TelaCadastro
		});

        // --- Ações ---
        btnCadastrar.addActionListener(e -> cadastrarProduto());
        btnRemover.addActionListener(e -> removerProduto());
        btnEditar.addActionListener(e -> editarProduto());

        carregarProdutos();
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
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            Produtos produto = new Produtos(0, nome, preco, quantidade);

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            dao.inserir(produto);
            BancoDeDados.desconectar(conn);

            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            carregarProdutos();

            txtNome.setText("");
            txtPreco.setText("");
            txtQuantidade.setText("");
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

            String novoNome = txtNovoNome.getText();
            double novoPreco = Double.parseDouble(txtNovoPreco.getText().replace(",", "."));
            int novaQuantidade = Integer.parseInt(txtNovaQuantidade.getText());

            Produtos produto = new Produtos(id, novoNome, novoPreco, novaQuantidade);

            Connection conn = BancoDeDados.conectar();
            ProdutosDAO dao = new ProdutosDAO(conn);
            dao.atualizar(produto);
            BancoDeDados.desconectar(conn);

            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            carregarProdutos();

            txtNovoNome.setText("");
            txtNovoPreco.setText("");
            txtNovaQuantidade.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + e.getMessage());
        }
    }
}
