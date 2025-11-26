package View;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.event.ActionListener;
import Controller.CadastroController;

public class TelaCadastro extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome;
    private JFormattedTextField txtCPF;
    private JCheckBox chckbxADM;
    private JButton btnCadastrar;
    private JButton btnCancelar;

    public TelaCadastro() {
        setTitle("Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Cadastro de Usuário");
        lblTitulo.setBounds(120, 11, 250, 36);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        contentPane.add(lblTitulo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(52, 59, 60, 19);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(98, 58, 240, 23);
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setBounds(54, 102, 40, 19);
        lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(lblCPF);

        try {
            MaskFormatter cpfMask = new MaskFormatter("###########");
            cpfMask.setPlaceholderCharacter('_');
            txtCPF = new JFormattedTextField(cpfMask);
            txtCPF.setBounds(98, 102, 120, 23);
            txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 14));
            contentPane.add(txtCPF);
        } catch (Exception e) {
            e.printStackTrace();
        }

        chckbxADM = new JCheckBox("Sou Administrador");
        chckbxADM.setBounds(50, 146, 200, 30);
        chckbxADM.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chckbxADM.setBackground(new Color(208, 192, 209));
        contentPane.add(chckbxADM);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(272, 197, 120, 30);
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnCadastrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(78, 197, 120, 30);
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnCancelar);

        // Inicializa o controller
        new CadastroController(this);
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

    public boolean isAdmin() {
        return chckbxADM.isSelected();
    }

    public void cadastrar(ActionListener actionListener) {
        this.btnCadastrar.addActionListener(actionListener);
    }

    public void cancelar(ActionListener actionListener) {
        this.btnCancelar.addActionListener(actionListener);
    }

    public void exibirMensagem(String titulo, String mensagem, int tipoMensagem) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipoMensagem);
    }

    public void limparCampos() {
        txtNome.setText("");
        txtCPF.setText("");
        chckbxADM.setSelected(false);
    }
}