package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Consumo;
import util.Util;

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
        
        Query q2 = manager.query();
        q2.constrain(Cliente.class);
        q2.descend("cpf").constrain(cpf2);
        
        List<Cliente> cliente2List = q2.execute();
        
        if (cliente1List.isEmpty() || cliente2List.isEmpty()) {
            System.out.println("Clientes não encontrados.");
        } else {
            Cliente cliente = cliente1List.get(0);
            Cliente cliente2 = cliente2List.get(0);
            
            System.out.println("Cliente encontrado: " + cliente);
            System.out.println("Cliente encontrado: " + cliente2);
            
            List<Consumo> temp = cliente.getItensConsumos();
            cliente.setConsumos(cliente2.getItensConsumos());
            cliente2.setConsumos(temp);
            
            manager.store(cliente);
            manager.store(cliente2);
            manager.commit();
            
            System.out.println("Relacionamentos alterados");
        }

        Util.desconectar();
        System.out.println("Desconectado.");
    }
}
