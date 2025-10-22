package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {

    // CREATE - Adicionar um novo usuário
    public void adicionarUsuario(Usuarios usuario) {
        String sql = "INSERT INTO usuarios (nome, cpf, isAdmin) VALUES (?, ?, ?)";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            conexao = BancoDeDados.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getCpf()); // cpf agora como String
            pstm.setBoolean(3, usuario.getIsAdmin());
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

    // READ - Buscar usuário por CPF
    public Usuarios buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conexao = BancoDeDados.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cpf);
            rs = pstm.executeQuery();

            if (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setIsAdmin(rs.getBoolean("isAdmin"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BancoDeDados.desconectar(conexao);
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}