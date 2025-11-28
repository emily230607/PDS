package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.event.ActionListener;
import Controller.LoginController;
import net.miginfocom.swing.MigLayout;

public class TelaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JFormattedTextField txtCPF;
    private JButton btnEntrar;
    private JButton btnCadastrar;

    public TelaLogin() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setMinimumSize(new Dimension(400, 250)); 
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        contentPane.setLayout(new MigLayout(
            "fill, insets 20", 
            "[right][grow]",  
            "[]20[]10[]20[]" 
        ));
        
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Informe seus dados");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        contentPane.add(lblTitulo, "span 2, align center, gapbottom 20, wrap");

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(lblNome, "");
        
        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNome.setColumns(10);
        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());
        contentPane.add(txtNome, "growx, wrap");

        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(lblCPF, "");

        try {
            MaskFormatter cpfMask = new MaskFormatter("###########");
            cpfMask.setPlaceholderCharacter('_');
            txtCPF = new JFormattedTextField(cpfMask);
            txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 14));
            contentPane.add(txtCPF, "width 150!, wrap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        btnCadastrar = new JButton("Me Cadastrar");
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnCadastrar, "skip, split 2, width 130!, gaptop 20");

        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnEntrar, "width 100!, gapleft 10");

        new LoginController(this);
    }

    private static class ApenasLetrasFilter extends DocumentFilter {
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

    public String getNome() {
        return txtNome.getText();
    }

    public String getCPF() {
        return txtCPF.getText();
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JFormattedTextField getTxtCpf() {
        return txtCPF;
    }

    public JButton getBtnEntrar() {
        return btnEntrar;
    }

    public JButton getBtnCadastrar() {
        return btnCadastrar;
    }

    public void entrar(ActionListener actionListener) {
        this.btnEntrar.addActionListener(actionListener);
    }

    public void cadastrar(ActionListener actionListener) {
        this.btnCadastrar.addActionListener(actionListener);
    }

    public void exibirMensagem(String titulo, String mensagem, int tipoMensagem) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipoMensagem);
    }
    
    public void limparCampos() {
        txtNome.setText("");
        txtCPF.setText("");
    }
}