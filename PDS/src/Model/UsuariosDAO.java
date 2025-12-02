package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO {

    public void adicionarUsuario(Usuarios usuario) throws RuntimeException {
        String sql = "INSERT INTO usuarios (nome, cpf, isAdmin) VALUES (?, ?, ?)";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome não pode ser vazio!");
            }
            
            if (usuario.getCpf() == null || usuario.getCpf().contains("_")) {
                throw new IllegalArgumentException("CPF inválido!");
            }

            conexao = BancoDeDados.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getCpf());
            pstm.setBoolean(3, usuario.getIsAdmin());
            pstm.executeUpdate();
            
            System.out.println("Usuário cadastrado com sucesso!");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
            throw new RuntimeException("Dados inválidos: " + e.getMessage());
            
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário no banco!");
            System.err.println("Detalhes: " + e.getMessage());
            e.printStackTrace();
            
            if (e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException("CPF já cadastrado no sistema!");
            }
            
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage());
            
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }
            BancoDeDados.desconectar(conexao);
        }
    }

    public Usuarios buscarPorCpf(String cpf) throws RuntimeException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            if (cpf == null || cpf.trim().isEmpty() || cpf.contains("_")) {
                throw new IllegalArgumentException("CPF inválido para busca!");
            }

            conexao = BancoDeDados.conectar();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, cpf);
            rs = pstm.executeQuery();

            if (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setIsAdmin(rs.getBoolean("isAdmin"));
                
                System.out.println("Usuário encontrado: " + usuario.getNome());
                return usuario;
            }
            
            System.out.println("Nenhum usuário encontrado com o CPF fornecido");
            return null;
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário no banco!");
            System.err.println("Detalhes: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
            BancoDeDados.desconectar(conexao);
        }
    }

    public Usuarios buscarPorId(int id) throws RuntimeException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID deve ser maior que zero!");
            }

            conexao = BancoDeDados.conectar();
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
            
            return null;
            
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID!");
            System.err.println("Detalhes: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
            BancoDeDados.desconectar(conexao);
        }
    }
}