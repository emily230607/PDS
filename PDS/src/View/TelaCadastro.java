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
	private Object cpfMask;

    public TelaCadastro() {
        setTitle("Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 453, 299);
        
      	setPreferredSize(new Dimension(450, 300));
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[100px,grow][200px,grow][100px,grow]", "[grow][grow][grow][grow][grow]"));
                        
                                JLabel lblTitulo = new JLabel("Cadastro de Usuário");
                                lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
                                contentPane.add(lblTitulo, "cell 1 0,grow");
                
                        JLabel lblNome = new JLabel("Nome:");
                        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
                        contentPane.add(lblNome, "cell 0 1,alignx right,aligny center");

        try {
            MaskFormatter cpfMask = new MaskFormatter("###########");
            cpfMask.setPlaceholderCharacter('_');
        } catch (Exception e) {
            e.printStackTrace();
        }
                        
                                txtNome = new JTextField();
                                txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());
                                contentPane.add(txtNome, "cell 1 1,growx,aligny center");
                                txtNome.setColumns(10);
                
                        JLabel lblCPF = new JLabel("CPF:");
                        lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 15));
                        contentPane.add(lblCPF, "cell 0 2,alignx right,aligny center");
                        txtCPF = new JFormattedTextField(cpfMask);
                        txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        contentPane.add(txtCPF, "cell 1 2,growx,aligny center");
                                        
                                                chckbxADM = new JCheckBox("Sou Administrador");
                                                chckbxADM.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                                chckbxADM.setBackground(new Color(208, 192, 209));
                                                contentPane.add(chckbxADM, "cell 1 3,grow");
                                
                                        btnCancelar = new JButton("Cancelar");
                                        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                        contentPane.add(btnCancelar, "flowx,cell 0 4,grow");
                        
                                btnCadastrar = new JButton("Cadastrar");
                                btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                contentPane.add(btnCadastrar, "cell 2 4,grow");

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