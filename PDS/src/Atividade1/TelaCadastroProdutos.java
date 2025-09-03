package Atividade1;

import java.util.ArrayList;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroProdutos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroProdutos frame = new TelaCadastroProdutos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCadastroProdutos() {
		setTitle("Cadastrar Produto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 493, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(208, 192, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cadastrar novo Produto");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(155, 11, 182, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblRemoverProduto = new JLabel("Remover Produto");
		lblRemoverProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRemoverProduto.setBounds(167, 132, 136, 25);
		contentPane.add(lblRemoverProduto);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(76, 56, 303, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setColumns(10);
		textField_1.setBounds(76, 99, 73, 25);
		contentPane.add(textField_1);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(10, 52, 56, 25);
		contentPane.add(lblNome);
		
		// Campo Preço (cadastro novo produto)
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setBounds(76, 99, 120, 25);
		contentPane.add(textField_1);
		((AbstractDocument) textField_1.getDocument()).setDocumentFilter(new DocumentFilter());

		// Adiciona prefixo R$ na frente
		JLabel lblMoeda = new JLabel("R$");
		lblMoeda.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMoeda.setBounds(55, 95, 20, 25);
		contentPane.add(lblMoeda);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(347, 98, 94, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Remover");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_1.setBounds(364, 204, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNome_1 = new JLabel("Selecionar Produto");
		lblNome_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome_1.setBounds(10, 168, 113, 25);
		contentPane.add(lblNome_1);
		
		JLabel lblEditarProduto = new JLabel("Editar Produto");
		lblEditarProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEditarProduto.setBounds(189, 227, 114, 25);
		contentPane.add(lblEditarProduto);
		
		JLabel lblNome_2 = new JLabel("Novo nome");
		lblNome_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome_2.setBounds(10, 309, 73, 25);
		contentPane.add(lblNome_2);
		
		// Campo Novo Preço (edição)
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_3.setBounds(88, 348, 120, 25);
		contentPane.add(textField_3);
		((AbstractDocument) textField_3.getDocument()).setDocumentFilter(new DocumentFilter());

		JLabel lblMoeda2 = new JLabel("R$");
		lblMoeda2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMoeda2.setBounds(65, 345, 20, 25);
		contentPane.add(lblMoeda2);
		
		JButton btnNewButton_2 = new JButton("Editar");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(290, 346, 89, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Finalizar");
		btnNewButton_3.setBackground(new Color(173, 145, 174));
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_3.setBounds(347, 395, 100, 30);
		contentPane.add(btnNewButton_3);
		
		JLabel lblNome_1_1 = new JLabel("Selecionar Produto");
		lblNome_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome_1_1.setBounds(10, 267, 113, 25);
		contentPane.add(lblNome_1_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_1.setBounds(123, 270, 256, 25);
		contentPane.add(comboBox_1);
		
		JButton btnNewButton_3_1 = new JButton("Cancelar");
		btnNewButton_3_1.setBackground(new Color(173, 145, 174));
		btnNewButton_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_3_1.setBounds(28, 395, 100, 30);
		contentPane.add(btnNewButton_3_1);
		
		JComboBox comboBox_1_1 = new JComboBox();
		comboBox_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_1_1.setBounds(136, 170, 256, 23);
		contentPane.add(comboBox_1_1);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_3.setColumns(10);
		textField_3.setBounds(88, 348, 73, 25);
		contentPane.add(textField_3);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_2.setColumns(10);
		textField_2.setBounds(89, 312, 303, 25);
		contentPane.add(textField_2);
	}
}
