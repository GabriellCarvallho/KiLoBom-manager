package com.rede.kilobom.repositorio;

import java.util.List;
import java.util.Optional;

import com.rede.kilobom.modelo.Consumo;
import com.rede.kilobom.util.Util;

import jakarta.persistence.TypedQuery;

public class ConsumoRepositorio extends CRUDRepositorio<Consumo> {

	@Override
    public List<Consumo> listar() {
        TypedQuery<Consumo> query = Util.getManager().createQuery(
            "SELECT c FROM Consumo c " +
            "LEFT JOIN FETCH c.cliente " +
            "LEFT JOIN FETCH c.filial",
            Consumo.class
        );
        return query.getResultList();
    }

    @Override
    public Optional<Consumo> ler(Object chave) {
        TypedQuery<Consumo> query = Util.getManager().createQuery(
            "SELECT c FROM Consumo c " +
            "LEFT JOIN FETCH c.cliente " +
            "LEFT JOIN FETCH c.filial " +
            "WHERE c.id = :id",
            Consumo.class
        );
        query.setParameter("id", (Integer) chave);

        List<Consumo> resultado = query.getResultList();
        if (resultado.isEmpty()) return Optional.empty();
        return Optional.of(resultado.get(0));
    }

    public List<Consumo> buscarConsumosCliente(String cpf) {
        TypedQuery<Consumo> query = Util.getManager().createQuery(
            "SELECT c FROM Consumo c " +
            "LEFT JOIN FETCH c.cliente cl " +
            "LEFT JOIN FETCH c.filial f " +
            "WHERE cl.cpf = :cpf",
            Consumo.class
        );
        query.setParameter("cpf", cpf);
        return query.getResultList();
    }

    public List<Consumo> buscarPorData(String data) {
        TypedQuery<Consumo> query = Util.getManager().createQuery(
            "SELECT c FROM Consumo c " +
            "LEFT JOIN FETCH c.cliente " +
            "LEFT JOIN FETCH c.filial " +
            "WHERE c.data = :data",
            Consumo.class
        );
        query.setParameter("data", data);
        return query.getResultList();
    }
}
