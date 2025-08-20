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
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

public class TelaInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial();
					frame.setVisible(true);
					System.out.println("dsfsdf");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaInicial() {
		setTitle("Cadastro/Login");
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
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(10, 59, 39, 19);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel_1);
		
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
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(56, 58, 240, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		((AbstractDocument) textField.getDocument()).setDocumentFilter(new ApenasLetrasFilter());
		
		
		
		
		JLabel lblNewLabel_1_1 = new JLabel("CPF");
		lblNewLabel_1_1.setBounds(12, 102, 25, 19);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel_1_1);
		
		MaskFormatter cpfMask = null;
		try {
		    cpfMask = new MaskFormatter("###########"); // 11 números
		    cpfMask.setPlaceholderCharacter('_'); // mostra "_" nos espaços vazios
		} catch (Exception e) {
		    e.printStackTrace();
		}

		JFormattedTextField textField_1 = new JFormattedTextField(cpfMask);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setBounds(55, 102, 98, 23);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(272, 197, 89, 30);
		contentPane.add(btnNewButton);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(78, 197, 89, 30);
		contentPane.add(btnCancelar);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Sou Administrador");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxNewCheckBox.setBounds(50, 146, 146, 30);
		contentPane.add(chckbxNewCheckBox);
	}
}
