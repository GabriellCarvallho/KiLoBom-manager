package appconsole;

import fachada.Fachada;

public class Apagar {

    public static void main(String[] args) {
    	String cpf = "111.111.111/11";
    	Fachada.deletarCliente(cpf);
    	
    	System.out.println("Cliente com CPF: " + cpf + " deletado com sucesso!");
    }
}
