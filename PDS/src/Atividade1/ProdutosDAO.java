package Atividade1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ProdutosDAO {
	
	// CREATE - Adicionar um novo produtos
    public void adicionarProduto(Produtos produtos) {
        String sql = "INSERT INTO produtos (nome, preco) VALUES (?, ?)";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            conexao = BancoDeDados.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, produtos.getNome());
            pstm.setFloat(2, produtos.getpreco());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	BancoDeDados.desconectar(conexao);
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // UPDATE - Atualizar um produtos existente
    public void atualizarProduto(Produtos produtos) {
        String sql = "UPDATE produtos SET nome = ?, email = ?";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            conexao = BancoDeDados.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, produtos.getNome());
            pstm.setFloat(2, produtos.getpreco());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	BancoDeDados.desconectar(conexao);
        }
    }
    
    // DELETE - Excluir um produtos pelo Nome
    public void excluirProduto(String nome) {
        String sql = "DELETE FROM produtos WHERE nome = ?";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            conexao = BancoDeDados.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, nome);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	BancoDeDados.desconectar(conexao);
        }
    }
    
}
