package Atividade1;

import java.util.ArrayList;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
	private JTextField textField_2;
	private JTextField textField_3;

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
		setBounds(100, 100, 491, 338);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(208, 192, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cadastrar novo Produto");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(155, 0, 155, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblRemoverProduto = new JLabel("Remover Produto");
		lblRemoverProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRemoverProduto.setBounds(167, 88, 122, 25);
		contentPane.add(lblRemoverProduto);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setBounds(76, 34, 143, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox.setBounds(123, 124, 176, 20);
		contentPane.add(comboBox);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_1.setColumns(10);
		textField_1.setBounds(76, 65, 73, 20);
		contentPane.add(textField_1);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(10, 30, 56, 25);
		contentPane.add(lblNome);
		
		JLabel lblPreo = new JLabel("Preço");
		lblPreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPreo.setBounds(10, 61, 42, 25);
		contentPane.add(lblPreo);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.setBounds(347, 64, 94, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Remover");
		btnNewButton_1.setBounds(352, 123, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNome_1 = new JLabel("Selecionar Produto");
		lblNome_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome_1.setBounds(10, 119, 103, 25);
		contentPane.add(lblNome_1);
		
		JLabel lblEditarProduto = new JLabel("Editar Produto");
		lblEditarProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEditarProduto.setBounds(187, 145, 100, 25);
		contentPane.add(lblEditarProduto);
		
		JLabel lblNome_2 = new JLabel("Novo nome");
		lblNome_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome_2.setBounds(10, 205, 73, 25);
		contentPane.add(lblNome_2);
		
		JLabel lblPreo_1 = new JLabel("Novo preço");
		lblPreo_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPreo_1.setBounds(10, 241, 73, 25);
		contentPane.add(lblPreo_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_2.setColumns(10);
		textField_2.setBounds(89, 209, 143, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_3.setColumns(10);
		textField_3.setBounds(93, 245, 73, 20);
		contentPane.add(textField_3);
		
		JButton btnNewButton_2 = new JButton("Editar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(352, 207, 89, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Finalizar");
		btnNewButton_3.setBackground(new Color(173, 145, 174));
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_3.setBounds(352, 258, 100, 30);
		contentPane.add(btnNewButton_3);
		
		JLabel lblNome_1_1 = new JLabel("Selecionar Produto");
		lblNome_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome_1_1.setBounds(10, 169, 103, 25);
		contentPane.add(lblNome_1_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_1.setBounds(123, 174, 176, 20);
		contentPane.add(comboBox_1);
		
		JButton btnNewButton_3_1 = new JButton("Cancelar");
		btnNewButton_3_1.setBackground(new Color(173, 145, 174));
		btnNewButton_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_3_1.setBounds(231, 258, 100, 30);
		contentPane.add(btnNewButton_3_1);
	}
}
