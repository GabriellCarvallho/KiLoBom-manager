package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Cliente;
import util.Util;

import java.util.List;

public class Alterar {

    public static void main(String[] args) {
        System.out.println("Conectando ao banco...");
        ObjectContainer manager = Util.conectar();

        String cpf = "98765432100"; // Cliente a alterar
        String novoNome = "Maria Silva";

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
            cliente.setNome(novoNome);
            manager.store(cliente);
            manager.commit();
            System.out.println("Cliente alterado para: " + cliente);
        }

        Util.desconectar();
        System.out.println("Desconectado.");
    }
}
