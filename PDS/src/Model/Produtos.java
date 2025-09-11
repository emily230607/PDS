package Atividade1;

public class Produtos {
	
	
		private float preco;
	    private String nome;

	    // Construtores
	    public Produtos() {}

	    public Produtos(float preco, String nome) {
	        this.preco = preco;
	        this.nome = nome;
	    }

	    // Getters e Setters
	    public float getpreco() {
	        return preco;
	    }

	    public void setpreco(float preco) {
	        this.preco = preco;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	}


