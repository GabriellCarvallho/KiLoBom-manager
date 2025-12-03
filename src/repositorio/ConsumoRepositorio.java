package repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.db4o.query.Query;

import modelo.Consumo;
import util.Util;

public class ConsumoRepositorio extends CRUDRepositorio<Consumo> {

	public ConsumoRepositorio() {
		super(Consumo.class);
	}

	@Override
	public Optional<Consumo> ler(Object chave) {
		Query query = Util.conectar().query();
		query.constrain(Consumo.class);
		query.descend("id").constrain(chave);
		
		List<Consumo> resultado = query.execute();
		return resultado.stream().findFirst();
	}
	
	public List<Consumo> buscarConsumosCliente(String cpf) {
		Query q2 = Util.conectar().query();
        q2.constrain(Consumo.class);
        q2.descend("cliente").descend("cpf").constrain(cpf);
        List<Consumo> consumosCliente = q2.execute();
        return new ArrayList<>(consumosCliente);
	}
	
	public List<Consumo> buscarPorData(String data){
		Query q1 = Util.conectar().query();
        q1.constrain(Consumo.class);
        q1.descend("data").constrain(data);
        List<Consumo> consumosData = q1.execute();
        return new ArrayList<>(consumosData);
	}

}
