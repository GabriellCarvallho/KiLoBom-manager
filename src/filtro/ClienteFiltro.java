package filtro;

import java.util.HashSet;
import java.util.Set;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;

import modelo.Cliente;

public class ClienteFiltro implements Evaluation {
	
	private final int quantidade;
	
	public ClienteFiltro(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public void evaluate(Candidate candidate) {
		Cliente cliente = (Cliente) candidate.getObject();
		
		Set<Integer> filiais = new HashSet<>();
	
		cliente.getConsumos().forEach((consumo) -> filiais.add(consumo.getFilial().getId()));
		
		if (filiais.size() > quantidade) 
			candidate.include(true);
		else 
			candidate.include(false);
	}

}
