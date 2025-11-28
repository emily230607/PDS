package Exception;

public class ValorInvalidoException extends Exception {
    public ValorInvalidoException(String campo, String motivo) {
        super("Valor inválido no campo '" + campo + "': " + motivo);
    }
    
    public ValorInvalidoException(String mensagem) {
        super(mensagem);
    }
}