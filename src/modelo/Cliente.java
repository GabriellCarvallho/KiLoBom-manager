package modelo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Cliente {
	
	private String cpf;
	private String nome;
	private List<Consumo> consumos;
	
	public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.consumos = new ArrayList<>();
    }
	
	public void adicionarConsumo(Consumo c) { 
		this.consumos.add(c); 
	}
	
    public void removerConsumo(Consumo c) { 
    	this.consumos.remove(c); 
    }

	public List<Consumo> getConsumos() {
		return this.consumos;
	}

	public void setConsumos(List<Consumo> consumos) {
		this.consumos = consumos;
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
	
	@Override
	public String toString() {
		return "CPF: " + this.cpf + 
				" Nome: " + this.nome + 
				" Consumos: " + consumos.stream().map((c) -> c.getId()).collect(Collectors.toList());
	}

}
