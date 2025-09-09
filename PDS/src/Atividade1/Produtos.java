package Atividade1;

public class Produtos {
	
	
		private int preco;
	    private String nome;

	    // Construtores
	    public Produtos() {}

	    public Produtos(int preco, String nome) {
	        this.preco = preco;
	        this.nome = nome;
	    }

	    // Getters e Setters
	    public int getpreco() {
	        return preco;
	    }

	    public void setpreco(int preco) {
	        this.preco = preco;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	}


