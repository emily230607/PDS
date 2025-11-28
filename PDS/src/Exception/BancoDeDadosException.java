package Exception;

public class BancoDeDadosException extends Exception {
    public BancoDeDadosException(String mensagem) {
        super(mensagem);
    }
    
    public BancoDeDadosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

