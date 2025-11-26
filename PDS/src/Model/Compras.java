package Model;

import java.sql.Timestamp;

public class Compras {
    
    private int id;
    private int usuarioId;
    private Timestamp dataCompra;
    private double total;

    public Compras() {}

    public Compras(int id, int usuarioId, Timestamp dataCompra, double total) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.dataCompra = dataCompra;
        this.total = total;
    }

    public Compras(int usuarioId, double total) {
        this.usuarioId = usuarioId;
        this.total = total;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Timestamp getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Timestamp dataCompra) {
        this.dataCompra = dataCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}