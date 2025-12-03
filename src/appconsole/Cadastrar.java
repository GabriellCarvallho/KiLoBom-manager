package appconsole;

import fachada.Fachada;
import modelo.Localizacao;

public class Cadastrar {

    public static void main(String[] args) {
    	System.out.println("Cadastrando Filiais...");
    	Fachada.criarFilial("Filial Centro", new Localizacao(1, 1));
    	Fachada.criarFilial("Filial Norte", new Localizacao(2, 2));
    	Fachada.criarFilial("Filial Sul", new Localizacao(3, 3));
    	
    	System.out.println("Filiais cadastradas.");
    	System.out.println("Cadastrando clientes...");
    	
    	Fachada.criarCliente("111.111.111/11", "Daniel");
    	Fachada.criarCliente("222.222.222/22", "Gabriel");
    	Fachada.criarCliente("333.333.333/33", "Jose");
    	
    	System.out.println("Clientes cadastrados.");
    	System.out.println("Cadastrando consumos...");
    	
    	Fachada.criarConsumo("2025-10-20", "Compra A", 150.0, 1, "111.111.111/11");
    	Fachada.criarConsumo("2025-10-21", "Compra B", 300.0, 1, "111.111.111/11");
    	Fachada.criarConsumo("2025-10-22", "Compra C", 40.0, 2, "111.111.111/11");
    	
    	Fachada.criarConsumo("2025-10-22", "Compra D", 150.0, 2, "222.222.222/22");
    	Fachada.criarConsumo("2025-10-22", "Compra E", 151.0, 2, "222.222.222/22");

    	System.out.println("Objetos cadastrados.");
    }
}
