package repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.db4o.query.Query;

import filtro.ClienteFiltro;
import modelo.Cliente;
import util.Util;

public class ClienteRepositorio extends CRUDRepositorio<Cliente> {

	public ClienteRepositorio() {
		super(Cliente.class);
	}

	@Override
	public Optional<Cliente> ler(Object chave) {
		Query query = Util.conectar().query();
		query.constrain(Cliente.class);
		query.descend("cpf").constrain(chave);
		
		List<Cliente> resultado = query.execute();
		return resultado.stream().findFirst();
	}
	
	public List<Cliente> buscarConsumouEmNFiliais(int n) {
		Query q3 = Util.conectar().query();
        q3.constrain(Cliente.class);
        q3.constrain(new ClienteFiltro(n));
        List<Cliente> clientes = q3.execute();
        return new ArrayList<>(clientes);
	}
	
	

}
