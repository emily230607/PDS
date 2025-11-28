package Exception;

public class UsuarioDuplicadoException extends Exception {
    public UsuarioDuplicadoException(String cpf) {
        super("Já existe um usuário cadastrado com o CPF: " + cpf);
    }
}
