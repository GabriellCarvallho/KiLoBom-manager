package com.rede.kilobom.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consumo34993")
public class Consumo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String data;

	private String descricao;

	@ManyToOne()
    @JoinColumn(name = "filial_id")
	private Filial filial;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_cpf")
	private Cliente cliente;

	private double valorpago;
	
	public Consumo() {}

	public Consumo (String data, String descricao, double valorpago) {
		this.data = data;
		this.descricao = descricao;
		this.valorpago = valorpago;
	}
	
	public int getId() {
		return id;
	}


	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValorpago() {
		return valorpago;
	}

	public void setValorpago(double valorpago) {
		this.valorpago = valorpago;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
    public String toString() {
        return "Consumo [id=" + id + ", cliente=" + (cliente != null ? cliente.getNome() : null) 
            + ", filial=" + (filial != null ? filial.getId() : null) 
            + ", data=" + data + ", valorpago=" + valorpago + "]";
    }
	
}
