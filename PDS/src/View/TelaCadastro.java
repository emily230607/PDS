package Atividade1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import net.miginfocom.swing.MigLayout;


import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

public class TelaCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
					frame.setVisible(true);
					System.out.println("dsfsdf");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void abrirTelaCadastroProdutos() {
	    dispose(); // Fecha a tela atual
	    new TelaCadastroProdutos().setVisible(true); // Abre a tela de cadastro
	}
	/**
	 * Create the frame.
	 */
	public TelaCadastro() {
		setTitle("Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(208, 192, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Informe ");
		lblNewLabel.setBounds(178, 11, 73, 36);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		contentPane.add(lblNewLabel);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 59, 39, 19);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNome);
		
		class ApenasLetrasFilter extends DocumentFilter {
		    @Override
		    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
		            throws BadLocationException {
		        if (string.matches("[a-zA-ZÀ-ÿ\\s]+")) { // aceita letras e espaço
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
		
		txtNome = new JTextField();
		txtNome.setBounds(56, 58, 240, 23);
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new ApenasLetrasFilter());
		
		
		
		
		JLabel lblCPF = new JLabel("CPF");
		lblCPF.setBounds(12, 102, 25, 19);
		lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblCPF);
		
		MaskFormatter cpfMask = null;
		try {
		    cpfMask = new MaskFormatter("###########"); // 11 números
		    cpfMask.setPlaceholderCharacter('_'); // mostra "_" nos espaços vazios
		} catch (Exception e) {
		    e.printStackTrace();
		}

		JFormattedTextField txtCPF = new JFormattedTextField(cpfMask);
		txtCPF.setBounds(55, 102, 98, 23);
		txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(txtCPF);
		
		JCheckBox chckbxADM = new JCheckBox("Sou Administrador");
		chckbxADM.setBounds(50, 146, 146, 30);
		chckbxADM.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(chckbxADM);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(272, 197, 89, 30);
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnEntrar);
		
		btnEntrar.addActionListener(e -> {
		    String nome = txtNome.getText();
		    String cpf = txtCPF.getText();
		    boolean isAdmin = chckbxADM.isSelected();

		    if (nome.isEmpty() || cpf.contains("_")) {
		        JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!");
		        return;
		    }

		    dispose(); // fecha a tela atual

		    if (isAdmin) {
		        new TelaCadastroProdutos().setVisible(true);
		    } else {
		        new TelaCompra().setVisible(true);
		    }
		});
		
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(78, 197, 89, 30);
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnCancelar);

		btnCancelar.addActionListener(e -> {
		    System.exit(0); // fecha o sistema
		});

	}
}
