package Atividade1;

public class Usuarios {
	
	    private int cpf;
	    private String nome;

	    // Construtores
	    public Usuarios() {}

	    public Usuarios(int cpf, String nome) {
	        this.cpf = cpf;
	        this.nome = nome;
	    }

	    // Getters e Setters
	    public int getcpf() {
	        return cpf;
	    }

	    public void setcpf(int cpf) {
	        this.cpf = cpf;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	}


