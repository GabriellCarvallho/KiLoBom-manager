package repositorio;

import java.util.List;
import java.util.Optional;

import com.db4o.query.Query;

import modelo.Filial;
import modelo.Localizacao;
import util.Util;

public class FilialRepositorio extends CRUDRepositorio<Filial>{

	public FilialRepositorio() {
		super(Filial.class);
	}

	@Override
	public Optional<Filial> ler(Object chave) {
		Query query = Util.conectar().query();
		query.constrain(Filial.class);
		query.descend("id").constrain(chave);
		
		List<Filial> resultado = query.execute();
		return resultado.stream().findFirst();
	}
	
	public Optional<Filial> buscarPelaLocalizacao(Localizacao localizacao) {
		Query query = Util.conectar().query();
		query.constrain(Filial.class);
		query.descend("localizacao").constrain(localizacao);
		
		List<Filial> resultado = query.execute();
		return resultado.stream().findFirst();
	}

}
