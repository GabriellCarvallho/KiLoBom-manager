package modelo;

import java.util.List;
import java.util.ArrayList;

public class Cliente {
	
	private List<Consumo> consumos;
	private String cpf;
	private String nome;
	
	public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.consumos = new ArrayList<>();
    }
	
	@Override
	public String toString() {
		return "Cpf: " + this.cpf + " Nome: " + this.nome + " itens de consumo: " + consumos.size();
	}
	
	public void adicionarItensConsumo(Consumo c) { 
		this.consumos.add(c); 
	}
	
    public void removerItensConsumo(Consumo c) { 
    	this.consumos.remove(c); 
    }

	public List<Consumo> getItensConsumos() {
		return new ArrayList<Consumo>(consumos);
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
