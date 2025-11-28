package Exception;

public class EstoqueInsuficienteException extends Exception {
    private int quantidadeDisponivel;
    private int quantidadeSolicitada;
    
    public EstoqueInsuficienteException(String produto, int disponivel, int solicitado) {
        super("Estoque insuficiente para o produto '" + produto + 
              "'. Disponível: " + disponivel + ", Solicitado: " + solicitado);
        this.quantidadeDisponivel = disponivel;
        this.quantidadeSolicitada = solicitado;
    }
    
    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
    
    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }
}
