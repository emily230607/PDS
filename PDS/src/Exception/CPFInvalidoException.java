package Exception;

public class CPFInvalidoException extends Exception {
    public CPFInvalidoException() {
        super("CPF inválido! Deve conter 11 dígitos numéricos.");
    }
    
    public CPFInvalidoException(String mensagem) {
        super(mensagem);
    }
}

