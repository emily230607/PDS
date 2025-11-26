package Model;

public class ItensCompra {
    
    private int id;
    private int compraId;
    private int produtoId;
    private int quantidade;
    private double subtotal;

    public ItensCompra() {}

    public ItensCompra(int id, int compraId, int produtoId, int quantidade, double subtotal) {
        this.id = id;
        this.compraId = compraId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }

    public ItensCompra(int compraId, int produtoId, int quantidade, double subtotal) {
        this.compraId = compraId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}