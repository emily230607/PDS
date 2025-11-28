// ==================== CampoVazioException.java ====================
package Exception;

public class CampoVazioException extends Exception {
    public CampoVazioException(String mensagem) {
        super(mensagem);
    }
    
    public CampoVazioException(String campo, String mensagem) {
        super("Campo '" + campo + "': " + mensagem);
    }
}
