package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Consumo;
import util.Util;

import java.util.List;

public class Apagar {

    public static void main(String[] args) {
        System.out.println("Conectando ao banco...");
        ObjectContainer manager = Util.conectar();

        String cpf = "12345678900"; // CPF do cliente que queremos apagar
        System.out.println("Buscando cliente com CPF: " + cpf);

        Query q = manager.query();
        q.constrain(Cliente.class);
        q.descend("cpf").constrain(cpf);
        List<Cliente> clientes = q.execute();

        if (clientes.size() == 0) {
            System.out.println("Cliente não encontrado.");
        } else {
            Cliente cliente = clientes.get(0);
            System.out.println("Cliente encontrado: " + cliente);

            System.out.println("Apagando consumos do cliente...");
            
            // Apaga consumos do cliente, e remove da filial para evitar órfãos
            for (Consumo consumo : cliente.getItensConsumos()) {
                consumo.getFilial().removerItensConsumo(consumo);
                cliente.removerItensConsumo(consumo);
                manager.delete(consumo);
                System.out.println("Consumo apagado: " + consumo);
            }

            System.out.println("Apagando cliente...");
            manager.delete(cliente);

            manager.commit();
            System.out.println("Cliente e consumos apagados com sucesso.");
        }

        Util.desconectar();
        System.out.println("Desconectado.");
    }
}
