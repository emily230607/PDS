package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {

	private Connection conn;

    public UsuariosDAO(Connection conn) {
        this.conn = conn;
    }

    // Inserir usuário
    public void inserir(Usuarios usuario) throws SQLException {
        String sql = "INSERT INTO Usuarios (nome, cpf, isAdmin) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getCpf());
        stmt.setBoolean(3, usuario.getIsAdmin());
        stmt.executeUpdate();
    }

    // Buscar usuário por CPF
    public Usuarios buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE cpf = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Usuarios usuario = new Usuarios();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setCpf(rs.getString("cpf"));
            usuario.setIsAdmin(rs.getBoolean("isAdmin"));
            return usuario;
        }
        return null;
    }

    // Listar todos
    public List<Usuarios> listarTodos() throws SQLException {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Usuarios usuario = new Usuarios();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setCpf(rs.getString("cpf"));
            usuario.setIsAdmin(rs.getBoolean("isAdmin"));
            lista.add(usuario);
        }
        return lista;
    }
}
