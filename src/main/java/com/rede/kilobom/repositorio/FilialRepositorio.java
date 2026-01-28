package com.rede.kilobom.repositorio;

import java.util.List;
import java.util.Optional;

import com.rede.kilobom.modelo.Filial;
import com.rede.kilobom.modelo.Localizacao;
import com.rede.kilobom.util.Util;

import jakarta.persistence.TypedQuery;

public class FilialRepositorio extends CRUDRepositorio<Filial>{

	 @Override
    public List<Filial> listar() {
        // LEFT JOIN FETCH para trazer os consumos junto e evitar LazyInitializationException
        TypedQuery<Filial> query = Util.getManager().createQuery(
            "SELECT DISTINCT f FROM Filial f LEFT JOIN FETCH f.consumos",
            Filial.class
        );
        return query.getResultList();
    }

    @Override
    public Optional<Filial> ler(Object chave) {
        TypedQuery<Filial> query = Util.getManager().createQuery(
            "SELECT f FROM Filial f LEFT JOIN FETCH f.consumos WHERE f.id = :id",
            Filial.class
        );
        query.setParameter("id", (Integer) chave);

        List<Filial> resultado = query.getResultList();
        if (resultado.isEmpty()) return Optional.empty();
        return Optional.of(resultado.get(0));
    }

    public Optional<Filial> buscarPelaLocalizacao(Localizacao localizacao) {
        TypedQuery<Filial> query = Util.getManager().createQuery(
            "SELECT f FROM Filial f LEFT JOIN FETCH f.consumos WHERE f.localizacao = :localizacao",
            Filial.class
        );
        query.setParameter("localizacao", localizacao);

        List<Filial> resultado = query.getResultList();
        if (resultado.isEmpty()) return Optional.empty();
        return Optional.of(resultado.get(0));
    }

    public Optional<Filial> buscarPeloNome(String nome) {
        TypedQuery<Filial> query = Util.getManager().createQuery(
            "SELECT f FROM Filial f LEFT JOIN FETCH f.consumos WHERE f.nome = :nome",
            Filial.class
        );
        query.setParameter("nome", nome);

        List<Filial> resultado = query.getResultList();
        if (resultado.isEmpty()) return Optional.empty();
        return Optional.of(resultado.get(0));
    }

}
