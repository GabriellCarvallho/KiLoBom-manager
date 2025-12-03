package modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Filial {
	
	private int id;
	private String nome;
	private Localizacao localizacao;
	private List<Consumo> consumos;
	
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
		return "Id: " + this.id + 
				" Localização: " + this.localizacao + 
				" Consumos: " + this.consumos.stream().map((c) -> c.getId()).collect(Collectors.toList());
	}
	
}
