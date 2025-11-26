package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.util.ArrayList;

import Controller.CompraController;
import Model.Usuarios;
import Model.Produtos;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCompra extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxProdutos;
    private JTextField txtQuantidade;
    private JTextArea areaCarrinho;
    private JLabel lblTotal;
    private JButton btnAdicionar, btnFinalizar, btnCancelar;

    private CompraController controller;
    private JButton btnExcluirProduto;

    public TelaCompra(Usuarios usuario) {
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

        // 🔹 Impede que o usuário digite letras no campo de quantidade
        ((AbstractDocument) txtQuantidade.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
                    throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.replace(fb, offset, length, string, attrs);
                }
            }
        });

        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnAdicionar.setBounds(470, 60, 100, 25);
        contentPane.add(btnAdicionar);

        areaCarrinho = new JTextArea();
        areaCarrinho.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(areaCarrinho);
        scroll.setBounds(30, 110, 540, 250);
        contentPane.add(scroll);

        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTotal.setBounds(423, 371, 126, 25);
        contentPane.add(lblTotal);

        btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.setBackground(new Color(173, 145, 174));
        btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnFinalizar.setBounds(419, 407, 151, 30);
        contentPane.add(btnFinalizar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(173, 145, 174));
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancelar.setBounds(30, 410, 127, 30);
        contentPane.add(btnCancelar);
        
        btnExcluirProduto = new JButton("Excluir Produto");
        btnExcluirProduto.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnExcluirProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExcluirProduto.setBackground(new Color(173, 145, 174));
        btnExcluirProduto.setBounds(30, 371, 127, 30);
        contentPane.add(btnExcluirProduto);

        // 🔹 Instancia o controller e passa os componentes
        controller = new CompraController(this, usuario);
        controller.carregarProdutos();

        // 🔹 Eventos dos botões
        btnAdicionar.addActionListener(e -> controller.adicionarAoCarrinho());
        btnFinalizar.addActionListener(e -> controller.finalizarCompra());
        btnCancelar.addActionListener(e -> controller.cancelar());
    }

    // ======== Getters usados pelo Controller ========

    public JComboBox<String> getComboBoxProdutos() {
        return comboBoxProdutos;
    }

    public JTextField getTxtQuantidade() {
        return txtQuantidade;
    }

    public JTextArea getAreaCarrinho() {
        return areaCarrinho;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }
}
