package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.event.ActionListener;
import Controller.CadastroController;
import net.miginfocom.swing.MigLayout;

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
        setSize(450, 300);
        setMinimumSize(new Dimension(400, 250));
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        contentPane.setLayout(new MigLayout( "fill, insets 20", "[right][grow]", "[]20[]10[]10[]20[]"
        ));
        
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Cadastro de Usuário");
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

        chckbxADM = new JCheckBox("Sou Administrador");
        chckbxADM.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chckbxADM.setBackground(new Color(208, 192, 209));
        contentPane.add(chckbxADM, "skip, wrap");

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnCancelar, "skip, split 2, width 120!, gaptop 20");

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(btnCadastrar, "width 120!, gapleft 10");

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