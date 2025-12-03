package repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.db4o.query.Query;

import util.Util;

public abstract class CRUDRepositorio<T> {	
	
	private final Class<T> type;
	
	protected CRUDRepositorio(Class<T> type) {
		this.type = type;
	}
	
	public void conectar() {
		Util.conectar();
	}
	
	public void desconectar() {
		Util.desconectar();
	}
	
	public void persistir(T objeto) {
		Util.getManager().store(objeto);
	}
	
	public void deletar(T objeto) {
		System.out.println(objeto);
		Util.getManager().delete(objeto);
	}
	
	public List<T> listar() {
		Query query = Util.getManager().query();
		query.constrain(type);
		List<T> resultado = query.execute();
		return new ArrayList<>(resultado);
	}
	
	public abstract Optional<T> ler(Object chave);
	
	public void begin() {}
	
	public void commit() {
		Util.getManager().commit();
	}
	
	public void rollback() {
		Util.getManager().rollback();
	}
	

}
