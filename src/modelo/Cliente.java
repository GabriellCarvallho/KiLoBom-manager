package modelo;

import java.util.List;
import java.util.ArrayList;

public class Cliente {
	
	private List<Consumo> itensConsumos;
	private String cpf;
	private String nome;
	
	public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.itensConsumos = new ArrayList<>();
    }
	
	@Override
	public String toString() {
		return "Cpf: " + this.cpf + " Nome: " + this.nome + " itens de consumo: " + itensConsumos.size();
	}
	
	public void adicionarItensConsumo(Consumo c) { 
		this.itensConsumos.add(c); 
	}
	
    public void removerItensConsumo(Consumo c) { 
    	this.itensConsumos.remove(c); 
    }

	public List<Consumo> getItensConsumos() {
		return itensConsumos;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
