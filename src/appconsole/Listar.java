package appconsole;

import fachada.Fachada;

public class Listar {

    public static void main(String[] args) {
    	
    	System.out.println("Listando registros:");
    	
    	System.out.println("\nClientes:");
    	Fachada.listarClientes().forEach(System.out::println);
    	
    	System.out.println("\nFiliais:");
    	Fachada.listarFiliais().forEach(System.out::println);
    	
    	System.out.println("\nConsumos");
    	Fachada.listarConsumos().forEach(System.out::println);
    	
    }
}
