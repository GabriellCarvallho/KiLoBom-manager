package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import filtros.ClientesFiltro;
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

        String data = "2025-10-20";
        System.out.println("\nBuscando consumos na data: " + data);
        Query q1 = manager.query();
        q1.constrain(Consumo.class);
        q1.descend("data").constrain(data);
        List<Consumo> consumosData = q1.execute();
        consumosData.forEach(System.out::println);

        String cpfCliente = "98765432100";
        System.out.println("\nBuscando consumos do cliente CPF: " + cpfCliente);
        Query q2 = manager.query();
        q2.constrain(Consumo.class);
        q2.descend("cliente").descend("cpf").constrain(cpfCliente);
        List<Consumo> consumosCliente = q2.execute();
        consumosCliente.forEach(System.out::println);

        int N = 1;
        System.out.println("\nBuscando clientes que consumiram em mais de " + N + " filiais.");
        Query q3 = manager.query();
        q3.constrain(Cliente.class);
        q3.constrain(new ClientesFiltro(N));
        List<Cliente> clientes = q3.execute();
        clientes.forEach(System.out::println);
        
        Util.desconectar();
        System.out.println("Desconectado.");
    }
}
