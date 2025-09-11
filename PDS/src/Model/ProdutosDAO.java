package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
	
	private Connection conn;

    public ProdutosDAO(Connection conn) {
        this.conn = conn;
    }

    // Inserir produto
    public void inserir(Produtos produto) throws SQLException {
        String sql = "INSERT INTO Produtos (nome, preco, quantidade) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getPreco());
        stmt.setInt(3, produto.getQuantidade());
        stmt.executeUpdate();
    }

    // Atualizar produto
    public void atualizar(Produtos produto) throws SQLException {
        String sql = "UPDATE Produtos SET nome=?, preco=?, quantidade=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getPreco());
        stmt.setInt(3, produto.getQuantidade());
        stmt.setInt(4, produto.getId());
        stmt.executeUpdate();
    }

    // Remover produto
    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM Produtos WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    // Buscar produto por ID
    public Produtos buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Produtos WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Produtos produto = new Produtos();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setQuantidade(rs.getInt("quantidade"));
            return produto;
        }
        return null;
    }

    // Listar todos os produtos
    public List<Produtos> listarTodos() throws SQLException {
        List<Produtos> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produtos";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Produtos produto = new Produtos();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setQuantidade(rs.getInt("quantidade"));
            lista.add(produto);
        }
        return lista;
    }
}