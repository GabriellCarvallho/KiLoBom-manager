package com.rede.kilobom.modelo;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;

@Entity
@Table(name = "cliente4593032")
public class Cliente {
	
	@Id
	private String cpf;
	private String nome;
	private byte[] foto;

	@OneToMany(mappedBy = "cliente")
	private List<Consumo> consumos;
	
	public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.consumos = new ArrayList<>();
    }

	public Cliente() {}
	
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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
    public String toString() {
        return "Cliente [cpf=" + cpf + ", nome=" + nome + ", consumos="
                + consumos.stream().map(c -> c.getId()).collect(Collectors.toList()) + "]";
    }

}
