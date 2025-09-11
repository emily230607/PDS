package View;

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
	private JTextField txtNome;
	private JTextField txtPreco;
	private JTextField textField_3;
	private JTextField tntNovoNome;
	private JTextField txtNovoPreço;

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
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNome.setBounds(76, 56, 316, 25);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(10, 52, 56, 25);
		contentPane.add(lblNome);
		
		// Campo Preço (cadastro novo produto)
		txtPreco = new JTextField();
		txtPreco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPreco.setBounds(76, 99, 65, 25);
		contentPane.add(txtPreco);
		((AbstractDocument) txtPreco.getDocument()).setDocumentFilter(new DocumentFilter());

		// Adiciona prefixo R$ na frente
		JLabel lblPreco = new JLabel("Preço");
		lblPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPreco.setBounds(10, 97, 43, 25);
		contentPane.add(lblPreco);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCadastrar.setBounds(298, 98, 94, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRemover.setBounds(303, 204, 89, 23);
		contentPane.add(btnRemover);
		
		JLabel lblSelecionarProduto = new JLabel("Selecionar Produto");
		lblSelecionarProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelecionarProduto.setBounds(10, 168, 113, 25);
		contentPane.add(lblSelecionarProduto);
		
		JLabel lblEditarProduto = new JLabel("Editar Produto");
		lblEditarProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEditarProduto.setBounds(189, 227, 114, 25);
		contentPane.add(lblEditarProduto);
		
		JLabel lblNovoNome = new JLabel("Novo nome");
		lblNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNovoNome.setBounds(10, 309, 73, 25);
		contentPane.add(lblNovoNome);

		JLabel lblNovoPreço = new JLabel("Novo preço");
		lblNovoPreço.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNovoPreço.setBounds(10, 349, 65, 25);
		contentPane.add(lblNovoPreço);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditar.setBounds(303, 350, 89, 23);
		contentPane.add(btnEditar);
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setBackground(new Color(173, 145, 174));
		btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFinalizar.setBounds(347, 395, 100, 30);
		contentPane.add(btnFinalizar);
		
		JLabel lblSelecionarProdutoEdição = new JLabel("Selecionar Produto");
		lblSelecionarProdutoEdição.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelecionarProdutoEdição.setBounds(10, 267, 113, 25);
		contentPane.add(lblSelecionarProdutoEdição);
		
		JComboBox comboBoxSelecionarProdutoEdição = new JComboBox();
		comboBoxSelecionarProdutoEdição.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxSelecionarProdutoEdição.setBounds(123, 270, 269, 25);
		contentPane.add(comboBoxSelecionarProdutoEdição);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(173, 145, 174));
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(28, 395, 100, 30);
		contentPane.add(btnCancelar);
		
		JComboBox comboBoxSelecionarProduto = new JComboBox();
		comboBoxSelecionarProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxSelecionarProduto.setBounds(136, 170, 256, 23);
		contentPane.add(comboBoxSelecionarProduto);
		
		tntNovoNome = new JTextField();
		tntNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tntNovoNome.setColumns(10);
		tntNovoNome.setBounds(89, 312, 303, 25);
		contentPane.add(tntNovoNome);
		
		txtNovoPreço = new JTextField();
		txtNovoPreço.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNovoPreço.setBounds(88, 348, 65, 25);
		contentPane.add(txtNovoPreço);
	}
}
