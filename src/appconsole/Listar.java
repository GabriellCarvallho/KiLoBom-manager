package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Consumo;
import modelo.Filial;
import util.Util;

import java.util.List;

public class Listar {

    public static void main(String[] args) {
        System.out.println("Conectando ao banco...");
        ObjectContainer manager = Util.conectar();

        System.out.println("\nListando Filiais:");
        Query qFilial = manager.query();
        qFilial.constrain(Filial.class);
        List<Filial> filiais = qFilial.execute();
        for (Filial f : filiais) {
            System.out.println(f);
        }

        System.out.println("\nListando Clientes:");
        Query qCliente = manager.query();
        qCliente.constrain(Cliente.class);
        List<Cliente> clientes = qCliente.execute();
        for (Cliente c : clientes) {
            System.out.println(c);
        }

        System.out.println("\nListando Consumos:");
        Query qConsumo = manager.query();
        qConsumo.constrain(Consumo.class);
        List<Consumo> consumos = qConsumo.execute();
        for (Consumo con : consumos) {
            System.out.println(con);
        }

        Util.desconectar();
        System.out.println("\nDesconectado.");
    }
}
