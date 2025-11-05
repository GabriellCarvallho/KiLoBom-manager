package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Consumo;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Alterar {

	public static void main(String[] args) {
        System.out.println("Conectando ao banco...");
        ObjectContainer manager = Util.conectar();

        String cpf = "98765432100"; // Cliente a alterar
        String cpf2 = "12345678900";
        System.out.println("Buscando cliente com CPF: " + cpf);
        
        Query q = manager.query();
        q.constrain(Cliente.class);
        q.descend("cpf").constrain(cpf);
        
        List<Cliente> cliente1List = q.execute();
       
        if (cliente1List.isEmpty()) {
            System.out.println("Cliente não encontrados.");
        } else {
            Cliente cliente = cliente1List.get(0);
            
            System.out.println("Cliente encontrado: " + cliente);

            cliente.setConsumos(new ArrayList<Consumo>());
            
            manager.store(cliente);
            
            manager.commit();
            
            System.out.println("Relacionamentos alterados");
        }

        Util.desconectar();
        System.out.println("Desconectado.");
    }
}
