package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Consumo;
import util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Consultar {

    public static void main(String[] args) {
        System.out.println("Conectando ao banco...");
        ObjectContainer manager = Util.conectar();

        String dataX = "2025-10-20";
        System.out.println("\nBuscando consumos na data: " + dataX);
        Query q1 = manager.query();
        q1.constrain(Consumo.class);
        q1.descend("data").constrain(dataX);
        List<Consumo> consumosData = q1.execute();
        for (Consumo c : consumosData) {
            System.out.println(c);
        }

        String cpfCliente = "98765432100";
        System.out.println("\nBuscando consumos do cliente CPF: " + cpfCliente);
        Query q2 = manager.query();
        q2.constrain(Consumo.class);
        q2.descend("cliente").descend("cpf").constrain(cpfCliente);
        List<Consumo> consumosCliente = q2.execute();
        for (Consumo c : consumosCliente) {
            System.out.println(c);
        }

        int N = 1;
        System.out.println("\nBuscando clientes que consumiram em mais de " + N + " filiais.");
        Query q3 = manager.query();
        q3.constrain(Cliente.class);
        List<Cliente> clientes = q3.execute();

        for (Cliente cliente : clientes) {
            Set<Integer> filiais = new HashSet<>();
            for (Consumo consumo : cliente.getItensConsumos()) {
                filiais.add(consumo.getFilial().getId());
            }
            if (filiais.size() > N) {
                System.out.println(cliente);
            }
        }

        Util.desconectar();
        System.out.println("Desconectado.");
    }
}
