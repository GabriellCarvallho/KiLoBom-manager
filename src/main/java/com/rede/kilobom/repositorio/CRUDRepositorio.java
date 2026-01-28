package com.rede.kilobom.repositorio;

import java.util.List;
import java.util.Optional;

import com.rede.kilobom.util.Util;

public abstract class CRUDRepositorio<T> {
	
	public void conectar() {
		Util.conectar();
	}
	
	public void desconectar() {
		Util.desconectar();
	}
	
	public void persistir(T objeto) {
		Util.getManager().persist(objeto);
	}

	public void atualizar(T objeto) {
		Util.getManager().merge(objeto);
	}
	
	public void deletar(T objeto) {
		Util.getManager().remove(objeto);
	}
	
	public abstract List<T> listar();
	public abstract Optional<T> ler(Object chave);
	
	public void begin() {
		if (!Util.getManager().getTransaction().isActive())
			Util.getManager().getTransaction().begin();
	}
	
	public void commit() {
		if (Util.getManager().getTransaction().isActive()) {
			Util.getManager().getTransaction().commit();
			Util.getManager().clear();
		}
	}
	
	public void rollback() {
		if (Util.getManager().getTransaction().isActive())
			Util.getManager().getTransaction().rollback();
	}
	

}
