package com.rede.kilobom.appconsole;

import com.rede.kilobom.fachada.Fachada;
import com.rede.kilobom.modelo.Localizacao;

public class Alterar {
    public static void main(String[] args) {
        System.out.println("=== ALTERANDO COM FACHADA ===");
        
        // Testar atualizações
        Fachada.atualizarCliente("111.111.111/11", "Daniel Atualizado");
        Fachada.atualizarFilial(1, new Localizacao(10.0, 10.0));
        Fachada.atualizarConsumo(1, "2025-12-25", "Natal", 500.0);
        
        System.out.println("Alterações realizadas!");
    }
}