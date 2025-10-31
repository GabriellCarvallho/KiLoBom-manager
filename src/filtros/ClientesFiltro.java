package filtros;

import java.util.HashSet;
import java.util.Set;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;

import modelo.Cliente;

public class ClientesFiltro implements Evaluation {
	
	private final int quantidade;
	
	public ClientesFiltro(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public void evaluate(Candidate candidate) {
		Cliente cliente = (Cliente) candidate.getObject();
		
		Set<Integer> filiais = new HashSet<>();
	
		cliente.getItensConsumos().forEach((consumo) -> filiais.add(consumo.getFilial().getId()));
		
		if (filiais.size() > quantidade) 
			candidate.include(true);
		else 
			candidate.include(false);
	}

}
