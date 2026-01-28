package com.rede.kilobom.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "filial458493")
public class Filial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nome;

	@Embedded
	private Localizacao localizacao;

	@OneToMany(mappedBy = "filial")
	private List<Consumo> consumos;
	
	public Filial() {}

	public Filial(String nome, Localizacao localizacao) {
		this.nome = nome;
		this.localizacao = localizacao;
		this.consumos = new ArrayList<>();
	}
	
	public void adicionarConsumo(Consumo c) { 
		this.consumos.add(c); 
	}
	
    public void removerConsumo(Consumo c) { 
    	this.consumos.remove(c); 
    }

	public int getId() {
		return id;
	}
	 public String getNome() { return nome; }
	 public void setNome(String nome) { this.nome = nome; }

	public List<Consumo> getConsumos() {
		return this.consumos;
	}
	
	public Localizacao getLocalizacao() { return localizacao; }
	public void setLocalizacao(Localizacao localizacao) { this.localizacao = localizacao; }
	
	@Override
    public String toString() {
        return "Filial [id=" + id + ", nome=" + nome + ", localizacao=" + localizacao + ", consumos="
                + consumos.stream().map(c -> c.getId()).collect(Collectors.toList()) + "]";
    }
	
}
