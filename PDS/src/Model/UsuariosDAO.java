package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Exception.*;

public class UsuariosDAO {

    public void adicionarUsuario(Usuarios usuario) throws BancoDeDadosException, UsuarioDuplicadoException {
        String sql = "INSERT INTO usuarios (nome, cpf, isAdmin) VALUES (?, ?, ?)";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            if (buscarPorCpf(usuario.getCpf()) != null) {
                throw new UsuarioDuplicadoException(usuario.getCpf());
            }

            conexao = BancoDeDados.conectar();
            
            if (conexao == null) {
                throw new BancoDeDadosException("Não foi possível conectar ao banco de dados.");
            }
            
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getCpf());
            pstm.setBoolean(3, usuario.getIsAdmin());
            pstm.executeUpdate();
            
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao cadastrar usuário no banco de dados.", e);
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }
            BancoDeDados.desconectar(conexao);
        }
    }

    public Usuarios buscarPorCpf(String cpf) throws BancoDeDadosException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conexao = BancoDeDados.conectar();
            
            if (conexao == null) {
                throw new BancoDeDadosException("Não foi possível conectar ao banco de dados.");
            }
            
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cpf);
            rs = pstm.executeQuery();

            if (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setIsAdmin(rs.getBoolean("isAdmin"));
                return usuario;
            }
            
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscar usuário por CPF.", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
            BancoDeDados.desconectar(conexao);
        }
        
        return null;
    }

    public Usuarios buscarPorId(int id) throws BancoDeDadosException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conexao = BancoDeDados.conectar();
            
            if (conexao == null) {
                throw new BancoDeDadosException("Não foi possível conectar ao banco de dados.");
            }
            
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setIsAdmin(rs.getBoolean("isAdmin"));
                return usuario;
            }
            
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscar usuário por ID.", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
            BancoDeDados.desconectar(conexao);
        }
        
        return null;
    }
}