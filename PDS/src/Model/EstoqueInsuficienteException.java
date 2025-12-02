package Model;

public class EstoqueInsuficienteException extends Exception {
    
    private int quantidadeDisponivel;
    private int quantidadeSolicitada;
    

    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }

    public EstoqueInsuficienteException(String mensagem, int quantidadeDisponivel, int quantidadeSolicitada) {
        super(mensagem);
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    @Override
    public String getMessage() {
        if (quantidadeDisponivel > 0 && quantidadeSolicitada > 0) {
            return super.getMessage() + 
                   "\nDisponível: " + quantidadeDisponivel + 
                   "\nSolicitado: " + quantidadeSolicitada;
        }
        return super.getMessage();
    }
    
    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
    
    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }
}