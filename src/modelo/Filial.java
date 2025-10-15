package modelo;
import java.util.ArrayList;
import java.util.List;

public class Filial {
	
	private int id;
	private Localizacao localizacao;
	private List<Consumo> itensConsumos;
	
	public Filial(int id, Localizacao localizacao) {
		this.id = id;
		this.localizacao = localizacao;
		this.itensConsumos = new ArrayList<>();
		
	}
	
	@Override
	public String toString() {
		return "Id: " + this.id + " Localização em: " + this.localizacao + " itens de consumo: " + this.itensConsumos.size();
	}
	
	public void adicionarItensConsumo(Consumo c) { 
		this.itensConsumos.add(c); 
	}
	
    public void removerItensConsumo(Consumo c) { 
    	this.itensConsumos.remove(c); 
    }

	public int getId() {
		return id;
	}

	public List<Consumo> getItensConsumos() {
		return itensConsumos;
	}
	
}
