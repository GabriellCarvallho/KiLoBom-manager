package modelo;

public class Consumo {
	
	private int id;
	private String data;
	private String descricao;
	private Filial filial;
	private Cliente cliente;
	private double valorpago;
	
	
	public Consumo (String data, String descricao, double valorpago, Filial filial, Cliente cliente) {
		this.data = data;
		this.descricao = descricao;
		this.valorpago = valorpago;
		this.filial = filial;
		this.cliente = cliente;
		filial.adicionarItensConsumo(this);
		cliente.adicionarItensConsumo(this);
		
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
		return "Id: " + this.id + 
				" Cliente: " + cliente.getNome() + 
				" Filial: " + filial.getId() + 
				" Data do consumo: " + this.data + 
				" Valor pago R$ " + this.valorpago;
	}
	
}
