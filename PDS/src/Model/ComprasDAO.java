package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComprasDAO {
    
    private Connection conn;

    public ComprasDAO(Connection conn) {
        this.conn = conn;
    }

    // Inserir compra e retornar o ID gerado
    public int inserir(Compras compra) throws SQLException {
        String sql = "INSERT INTO Compras (usuario_id, total) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, compra.getUsuarioId());
        stmt.setDouble(2, compra.getTotal());
        stmt.executeUpdate();
        
        // Recupera o ID gerado
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    // Buscar compra por ID
    public Compras buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Compras WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Compras compra = new Compras();
            compra.setId(rs.getInt("id"));
            compra.setUsuarioId(rs.getInt("usuario_id"));
            compra.setDataCompra(rs.getTimestamp("data_compra"));
            compra.setTotal(rs.getDouble("total"));
            return compra;
        }
        return null;
    }

    // Listar todas as compras de um usuário
    public List<Compras> listarPorUsuario(int usuarioId) throws SQLException {
        List<Compras> lista = new ArrayList<>();
        String sql = "SELECT * FROM Compras WHERE usuario_id=? ORDER BY data_compra DESC";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Compras compra = new Compras();
            compra.setId(rs.getInt("id"));
            compra.setUsuarioId(rs.getInt("usuario_id"));
            compra.setDataCompra(rs.getTimestamp("data_compra"));
            compra.setTotal(rs.getDouble("total"));
            lista.add(compra);
        }
        return lista;
    }

    // Listar todas as compras
    public List<Compras> listarTodas() throws SQLException {
        List<Compras> lista = new ArrayList<>();
        String sql = "SELECT * FROM Compras ORDER BY data_compra DESC";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Compras compra = new Compras();
            compra.setId(rs.getInt("id"));
            compra.setUsuarioId(rs.getInt("usuario_id"));
            compra.setDataCompra(rs.getTimestamp("data_compra"));
            compra.setTotal(rs.getDouble("total"));
            lista.add(compra);
        }
        return lista;
    }
}