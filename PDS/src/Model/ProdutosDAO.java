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

    public ProdutosDAO(Connection conn) throws IllegalArgumentException {
        if (conn == null) {
            throw new IllegalArgumentException("Conexão não pode ser nula!");
        }
        this.conn = conn;
    }

    public void inserir(Produtos produto) throws SQLException {
        String sql = "INSERT INTO Produtos (nome, preco, quantidade) VALUES (?, ?, ?)";
        PreparedStatement stmt = null;
        
        try {
            if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do produto não pode ser vazio!");
            }
            
            if (produto.getPreco() <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero!");
            }
            
            if (produto.getQuantidade() < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa!");
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.executeUpdate();
            
            System.out.println("Produto inserido com sucesso!");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validação falhou: " + e.getMessage());
            throw e;
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto no banco!");
            System.err.println("Detalhes: " + e.getMessage());
            throw e;
            
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar statement: " + e.getMessage());
                }
            }
        }
    }

    public void atualizar(Produtos produto) throws SQLException {
        String sql = "UPDATE Produtos SET nome=?, preco=?, quantidade=? WHERE id=?";
        PreparedStatement stmt = null;
        
        try {
            if (produto.getId() <= 0) {
                throw new IllegalArgumentException("ID inválido!");
            }
            
            if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome não pode ser vazio!");
            }
            
            if (produto.getPreco() <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero!");
            }
            
            if (produto.getQuantidade() < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa!");
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas == 0) {
                throw new SQLException("Produto não encontrado para atualização!");
            }
            
            System.out.println("Produto atualizado com sucesso!");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validação falhou: " + e.getMessage());
            throw e;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto!");
            System.err.println("Detalhes: " + e.getMessage());
            throw e;
            
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar statement: " + e.getMessage());
                }
            }
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM Produtos WHERE id=?";
        PreparedStatement stmt = null;
        
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID deve ser maior que zero!");
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas == 0) {
                throw new SQLException("Produto não encontrado para remoção!");
            }
            
            System.out.println("Produto removido com sucesso!");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validação falhou: " + e.getMessage());
            throw e;
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover produto!");
            System.err.println("Detalhes: " + e.getMessage());
            throw e;
            
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar statement: " + e.getMessage());
                }
            }
        }
    }

    public Produtos buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Produtos WHERE id=?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID deve ser maior que zero!");
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Produtos produto = new Produtos();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                return produto;
            }
            
            return null;
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validação falhou: " + e.getMessage());
            throw e;
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto!");
            System.err.println("Detalhes: " + e.getMessage());
            throw e;
            
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar statement: " + e.getMessage());
                }
            }
        }
    }

    public List<Produtos> listarTodos() throws SQLException {
        List<Produtos> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produtos ORDER BY nome";
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Produtos produto = new Produtos();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                lista.add(produto);
            }
            
            System.out.println(" " + lista.size() + " produto(s) carregado(s)");
            return lista;
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos!");
            System.err.println("Detalhes: " + e.getMessage());
            throw e;
            
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar statement: " + e.getMessage());
                }
            }
        }
    }
}