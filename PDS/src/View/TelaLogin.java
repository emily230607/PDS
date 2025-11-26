package View;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.event.ActionListener;
import Controller.LoginController;

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
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Informe seus dados");
        lblTitulo.setBounds(120, 11, 250, 36);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        contentPane.add(lblTitulo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(57, 82, 60, 19);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(121, 80, 240, 23);
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(txtNome);
        txtNome.setColumns(10);
        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());

        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setBounds(59, 132, 40, 19);
        lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(lblCPF);

        try {
            MaskFormatter cpfMask = new MaskFormatter("###########");
            cpfMask.setPlaceholderCharacter('_');
            txtCPF = new JFormattedTextField(cpfMask);
            txtCPF.setBounds(121, 130, 120, 23);
            txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 14));
            contentPane.add(txtCPF);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(272, 197, 100, 30);
        btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnEntrar);

        btnCadastrar = new JButton("Me Cadastrar");
        btnCadastrar.setBounds(78, 197, 130, 30);
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnCadastrar);

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