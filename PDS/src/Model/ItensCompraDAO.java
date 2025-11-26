package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItensCompraDAO {
    
    private Connection conn;

    public ItensCompraDAO(Connection conn) {
        this.conn = conn;
    }

    // Inserir item da compra
    public void inserir(ItensCompra item) throws SQLException {
        String sql = "INSERT INTO ItensCompra (compra_id, produto_id, quantidade, subtotal) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, item.getCompraId());
        stmt.setInt(2, item.getProdutoId());
        stmt.setInt(3, item.getQuantidade());
        stmt.setDouble(4, item.getSubtotal());
        stmt.executeUpdate();
    }

    // Listar itens de uma compra específica
    public List<ItensCompra> listarPorCompra(int compraId) throws SQLException {
        List<ItensCompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM ItensCompra WHERE compra_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, compraId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ItensCompra item = new ItensCompra();
            item.setId(rs.getInt("id"));
            item.setCompraId(rs.getInt("compra_id"));
            item.setProdutoId(rs.getInt("produto_id"));
            item.setQuantidade(rs.getInt("quantidade"));
            item.setSubtotal(rs.getDouble("subtotal"));
            lista.add(item);
        }
        return lista;
    }

    // Buscar item por ID
    public ItensCompra buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM ItensCompra WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            ItensCompra item = new ItensCompra();
            item.setId(rs.getInt("id"));
            item.setCompraId(rs.getInt("compra_id"));
            item.setProdutoId(rs.getInt("produto_id"));
            item.setQuantidade(rs.getInt("quantidade"));
            item.setSubtotal(rs.getDouble("subtotal"));
            return item;
        }
        return null;
    }
}