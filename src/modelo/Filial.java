package modelo;
import java.util.ArrayList;
import java.util.List;

public class Filial {
	
	private int id;
	private Localizacao localizacao;
	private List<Consumo> consumos;
	
	public Filial(Localizacao localizacao) {
		this.localizacao = localizacao;
		this.consumos = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "Id: " + this.id + " Localização em: " + this.localizacao + " itens de consumo: " + this.consumos.size();
	}
	
	public void adicionarItensConsumo(Consumo c) { 
		this.consumos.add(c); 
	}
	
    public void removerItensConsumo(Consumo c) { 
    	this.consumos.remove(c); 
    }

	public int getId() {
		return id;
	}

	public List<Consumo> getItensConsumos() {
		return new ArrayList<>(this.itensConsumos);
	}
	
}
