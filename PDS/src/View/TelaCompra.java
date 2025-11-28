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
import net.miginfocom.swing.MigLayout;

public class TelaCompra extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxProdutos;
    private JTextField txtQuantidade;
    private JTextArea areaCarrinho;
    private JLabel lblTotal;
    private JButton btnAdicionar, btnFinalizar, btnCancelar;

    private CompraController controller;

    public TelaCompra(Usuarios usuario) {
        setTitle("Tela de Compra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setMinimumSize(new Dimension(650, 450));
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        contentPane.setLayout(new MigLayout("fill, insets 15", "[right][grow][right][60][100]", "[]15[]15[grow]15[][]"));
        
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Compra de Produtos");
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(lblTitulo, "cell 0 0 5 1,alignx center");

        JLabel lblProduto = new JLabel("Produto:");
        lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblProduto, "cell 0 1");

        comboBoxProdutos = new JComboBox<>();
        comboBoxProdutos.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(comboBoxProdutos, "cell 1 1,growx");

        JLabel lblQtd = new JLabel("Qtd:");
        lblQtd.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblQtd, "cell 2 1,gapx 10");

        txtQuantidade = new JTextField();
        txtQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(txtQuantidade, "cell 3 1,width 60!");

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
        contentPane.add(btnAdicionar, "cell 4 1,width 100!");

        areaCarrinho = new JTextArea();
        areaCarrinho.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaCarrinho.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaCarrinho);
        contentPane.add(scroll, "cell 0 2 5 1,grow");

        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPane.add(lblTotal, "cell 0 3");

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(173, 145, 174));
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnCancelar, "cell 0 4,width 130!");

        btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.setBackground(new Color(173, 145, 174));
        btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnFinalizar, "cell 4 4,width 150!,alignx right");

        controller = new CompraController(this, usuario);
        controller.carregarProdutos();

        btnAdicionar.addActionListener(e -> controller.adicionarAoCarrinho());
        btnFinalizar.addActionListener(e -> controller.finalizarCompra());
        btnCancelar.addActionListener(e -> controller.cancelar());
    }

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