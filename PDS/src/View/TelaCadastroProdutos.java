package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import Controller.ProdutoController;
import net.miginfocom.swing.MigLayout;

public class TelaCadastroProdutos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome, txtPreco, txtQuantidade;
    private JTextField txtNovoNome, txtNovoPreco, txtNovaQuantidade;
    private JComboBox<String> comboBoxSelecionarProduto, comboBoxSelecionarProdutoEdicao;
    private JButton btnCadastrar, btnRemover, btnEditar, btnCancelar, btnFinalizar;

    public TelaCadastroProdutos() {
        setTitle("Cadastrar Produto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setMinimumSize(new Dimension(550, 500));
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(208, 192, 209));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        contentPane.setLayout(new MigLayout(
            "fill, insets 10",
            "[grow]",
            "[]10[]5[]5[]15[]5[]15[]5[]5[]5[]20[]"
        ));
        
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Cadastrar novo Produto");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(lblNewLabel, "align center, wrap");

        JPanel panelNome = new JPanel(new MigLayout("insets 0", "[][grow]", "[]"));
        panelNome.setBackground(new Color(208, 192, 209));
        
        JLabel lblNome = new JLabel("Nome");
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelNome.add(lblNome, "");
        
        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelNome.add(txtNome, "growx");
        
        contentPane.add(panelNome, "growx, wrap");

        JPanel panelCadastro = new JPanel(new MigLayout("insets 0", "[][65][20][80][grow][120]", "[]"));
        panelCadastro.setBackground(new Color(208, 192, 209));
        
        JLabel lblPreco = new JLabel("Preço");
        lblPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelCadastro.add(lblPreco, "");
        
        txtPreco = new JTextField();
        txtPreco.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelCadastro.add(txtPreco, "width 65!");
        
        JLabel lblQuantidade = new JLabel("Quantidade");
        lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelCadastro.add(lblQuantidade, "gapleft 20");
        
        txtQuantidade = new JTextField();
        txtQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelCadastro.add(txtQuantidade, "width 50!");
        
        panelCadastro.add(new JLabel(""), "growx"); 
        
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelCadastro.add(btnCadastrar, "width 120!");
        
        contentPane.add(panelCadastro, "growx, wrap");

        JLabel lblRemoverProduto = new JLabel("Remover Produto");
        lblRemoverProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(lblRemoverProduto, "align center, gaptop 10, wrap");

        JPanel panelRemover = new JPanel(new MigLayout("insets 0", "[grow][100]", "[]"));
        panelRemover.setBackground(new Color(208, 192, 209));
        
        comboBoxSelecionarProduto = new JComboBox<>();
        comboBoxSelecionarProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelRemover.add(comboBoxSelecionarProduto, "growx");
        
        btnRemover = new JButton("Remover");
        btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelRemover.add(btnRemover, "width 100!");
        
        contentPane.add(panelRemover, "growx, wrap");

        JLabel lblEditarProduto = new JLabel("Editar Produto");
        lblEditarProduto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(lblEditarProduto, "align center, gaptop 10, wrap");

        comboBoxSelecionarProdutoEdicao = new JComboBox<>();
        comboBoxSelecionarProdutoEdicao.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(comboBoxSelecionarProdutoEdicao, "growx, wrap");

        JPanel panelNovoNome = new JPanel(new MigLayout("insets 0", "[][grow]", "[]"));
        panelNovoNome.setBackground(new Color(208, 192, 209));
        
        JLabel lblNovoNome = new JLabel("Novo nome");
        lblNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelNovoNome.add(lblNovoNome, "");
        
        txtNovoNome = new JTextField();
        txtNovoNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelNovoNome.add(txtNovoNome, "growx");
        
        contentPane.add(panelNovoNome, "growx, wrap");

        JPanel panelEdicao = new JPanel(new MigLayout("insets 0", "[][100][20][80][grow][100]", "[]"));
        panelEdicao.setBackground(new Color(208, 192, 209));
        
        JLabel lblNovoPreco = new JLabel("Novo preço");
        lblNovoPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelEdicao.add(lblNovoPreco, "");
        
        txtNovoPreco = new JTextField();
        txtNovoPreco.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelEdicao.add(txtNovoPreco, "width 100!");
        
        JLabel lblNovaQtd = new JLabel("Nova qtd");
        lblNovaQtd.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelEdicao.add(lblNovaQtd, "gapleft 20");
        
        txtNovaQuantidade = new JTextField();
        txtNovaQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelEdicao.add(txtNovaQuantidade, "width 80!");
        
        panelEdicao.add(new JLabel(""), "growx"); 
        
        btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panelEdicao.add(btnEditar, "width 100!");
        
        contentPane.add(panelEdicao, "growx, wrap");

        JPanel panelBotoes = new JPanel(new MigLayout("insets 0", "[120][grow][120]", "[]"));
        panelBotoes.setBackground(new Color(208, 192, 209));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(173, 145, 174));
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelBotoes.add(btnCancelar, "width 120!");
        
        panelBotoes.add(new JLabel(""), "growx"); 
        
        btnFinalizar = new JButton("Finalizar");
        btnFinalizar.setBackground(new Color(173, 145, 174));
        btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panelBotoes.add(btnFinalizar, "width 120!");
        
        contentPane.add(panelBotoes, "growx, gaptop 20");

        new ProdutoController(this);
    }

    public String getNome() { 
    return txtNome.getText(); }
    
    public String getPreco() { 
    return txtPreco.getText(); }
    
    public String getQuantidade() { 
    return txtQuantidade.getText(); }
    
    public String getNovoNome() {
    return txtNovoNome.getText(); }
    
    public String getNovoPreco() { 
    	return txtNovoPreco.getText(); }
    
    public String getNovaQuantidade() { 
    	return txtNovaQuantidade.getText(); }

    public JComboBox<String> getComboRemover() { 
    	return comboBoxSelecionarProduto; }
    
    public JComboBox<String> getComboEditar() { 
    	return comboBoxSelecionarProdutoEdicao; }

    public void limparCampos() {
        txtNome.setText("");
        txtPreco.setText("");
        txtQuantidade.setText("");
        txtNovoNome.setText("");
        txtNovoPreco.setText("");
        txtNovaQuantidade.setText("");
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void addCadastrarListener(ActionListener listener) { 
    btnCadastrar.addActionListener(listener); }
    public void addRemoverListener(ActionListener listener) { 
    btnRemover.addActionListener(listener); }
    public void addEditarListener(ActionListener listener) { 
    btnEditar.addActionListener(listener); }
    public void addCancelarListener(ActionListener listener) { 
    btnCancelar.addActionListener(listener); }
    public void addFinalizarListener(ActionListener listener) { 
    btnFinalizar.addActionListener(listener); }
}