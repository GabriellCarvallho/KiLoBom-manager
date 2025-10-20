package appconsole;

import com.db4o.ObjectContainer;

import modelo.Cliente;
import modelo.Consumo;
import modelo.Filial;
import modelo.Localizacao;
import util.Util;

public class Cadastrar {

    public static void main(String[] args) {
        System.out.println("Conectando ao banco...");
        ObjectContainer manager = Util.conectar();

        System.out.println("Criando filiais...");
        Filial f1 = new Filial(new Localizacao(-23.56, -46.65));
        Filial f2 = new Filial(new Localizacao(-22.90, -43.20));
        manager.store(f1);
        manager.store(f2);

        System.out.println("Criando clientes...");
        Cliente c1 = new Cliente("12345678900", "João");
        Cliente c2 = new Cliente("98765432100", "Maria");
        manager.store(c1);
        manager.store(c2);

        System.out.println("Criando consumos...");
        Consumo cons1 = new Consumo("2025-10-20", "Compra A", 100.0, f1, c1);
        Consumo cons2 = new Consumo("2025-10-21", "Compra B", 150.0, f2, c1);
        Consumo cons3 = new Consumo("2025-10-20", "Compra C", 200.0, f1, c2);
        manager.store(cons1);
        manager.store(cons2);
        manager.store(cons3);

        manager.commit();
        System.out.println("Objetos cadastrados com sucesso.");

        Util.desconectar();
        System.out.println("Desconectado.");
    }
}
