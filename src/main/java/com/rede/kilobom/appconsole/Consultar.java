package com.rede.kilobom.appconsole;

import com.rede.kilobom.fachada.Fachada;

public class Consultar {

    public static void main(String[] args) {
    	System.out.println("Realizando consultas:");
    	
    	String data = "2025-10-22";
    	System.out.println("\nBuscando consumos na data: " + data);
    	Fachada.buscarConsumosData(data).forEach(System.out::println);
    	
    	String cpf = "222.222.222/22";
    	System.out.println("\nBuscando consumos do cliente com CPF: " + cpf);
    	Fachada.buscarConsumosCliente(cpf).forEach(System.out::println);
    	
    	int N = 1;
    	System.out.println("\nBuscando cliente que consumiram em mais de: " + N + " filiais.");
    	Fachada.buscarConsumouEmNFiliais(N).forEach(System.out::println);
    }
}
