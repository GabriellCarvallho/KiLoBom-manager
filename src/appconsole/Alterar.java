package appconsole;

import com.db4o.ObjectContainer;
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
        String novoNome = "Maria Silva";
        int consumoIdPessoa = 1;

        System.out.println("Buscando cliente com CPF: " + " e Consumo ID Usuário: " + consumoIdPessoa);

        Query q = manager.query();
        q.constrain(Cliente.class);
        q.descend("cpf").constrain(cpf);
        List<Cliente> clientes = q.execute();
        
        Query c = manager.query();
        c.constrain(Consumo.class);
        c.descend("id").constrain(consumoIdPessoa);
        List<Consumo> cs = c.execute();
        
        

        if (clientes.size() == 0 || cs.size() == 0) {
            System.out.println("Cliente ou id Consumo não encontrado.");
        } else {
            Cliente cliente = clientes.get(0);
            Consumo consumo = cs.get(0);
            
            System.out.println("Cliente encontrado: " + cliente.getNome() + " | Consumos antes: " + cliente.getItensConsumos().size());
            
            if (cliente.getItensConsumos().contains(consumo)) {
	            	cliente.removerItensConsumo(consumo);
	            	consumo.setCliente(null);
	            	cliente.setNome(novoNome);
	            	manager.store(cliente);
	                manager.commit();
	                System.out.println("ID alvo: " + consumoIdPessoa + " removido com sucesso!! ");
	                System.out.println("Cliente alterado para: " + cliente.getNome());
	                System.out.println("Itens de Consumos restantes após: " + cliente.getItensConsumos().size());
	                
	          
            }else {
                System.out.println("OID consumo? " + consumoIdPessoa + " não estava conectado e associado a esse cliente.");
           }

            
        }
        Util.desconectar();
        System.out.println("Desconectado.");
    }
}
