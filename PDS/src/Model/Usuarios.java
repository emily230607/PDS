package Model;

public class Usuarios {
	
	private int id;
    private String nome;
    private String cpf;
    private boolean isAdmin;

    public Usuarios() {}

    public Usuarios(int id, String nome, String cpf, boolean isAdmin) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.isAdmin = isAdmin;
    }

    public Usuarios(String nome, String cpf, boolean isAdmin) {
        this.nome = nome;
        this.cpf = cpf;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}