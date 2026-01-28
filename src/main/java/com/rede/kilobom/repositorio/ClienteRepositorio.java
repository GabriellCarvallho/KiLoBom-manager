package com.rede.kilobom.repositorio;

import java.util.List;
import java.util.Optional;

import com.rede.kilobom.modelo.Cliente;
import com.rede.kilobom.util.Util;

import jakarta.persistence.TypedQuery;

public class ClienteRepositorio extends CRUDRepositorio<Cliente> {

	@Override
    public List<Cliente> listar() {
        TypedQuery<Cliente> query = Util.getManager().createQuery(
            "SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.consumos",
            Cliente.class
        );
        return query.getResultList();
    }

    @Override
    public Optional<Cliente> ler(Object chave) {
        TypedQuery<Cliente> query = Util.getManager().createQuery(
            "SELECT c FROM Cliente c LEFT JOIN FETCH c.consumos WHERE c.cpf = :cpf",
            Cliente.class
        );
        query.setParameter("cpf", (String) chave);

        List<Cliente> resultado = query.getResultList();
        if (resultado.isEmpty()) return Optional.empty();
        return Optional.of(resultado.get(0));
    }

    public List<Cliente> buscarConsumouEmNFiliais(int n) {
        TypedQuery<Cliente> query = Util.getManager().createQuery(
            "SELECT c FROM Cliente c JOIN c.consumos con GROUP BY c HAVING COUNT(DISTINCT con.filial) >= :n",
            Cliente.class
        );
        query.setParameter("n", n);

        return query.getResultList();
    }
	

}
