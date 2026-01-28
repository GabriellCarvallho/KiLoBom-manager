package com.rede.kilobom.appswing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

public class TelaPrincipal {
    private JFrame frame;
    private JMenu mnCliente;
    private JMenu mnFilial;
    private JMenu mnConsumo;
    private JMenu mnConsulta;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaPrincipal window = new TelaPrincipal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaPrincipal() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Rede Consumo - Sistema de Gerenciamento");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        
        JLabel label = new JLabel("Sistema Rede Consumo");
        label.setFont(new Font("Tahoma", Font.BOLD, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(new java.awt.Color(0, 102, 204));
        label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.getContentPane().add(label);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        mnCliente = new JMenu("Cliente");
        mnCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaCliente();
            }
        });
        menuBar.add(mnCliente);
        
        mnFilial = new JMenu("Filial");
        mnFilial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaFilial();
            }
        });
        menuBar.add(mnFilial);

        mnConsumo = new JMenu("Consumo");
        mnConsumo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaConsumo();
            }
        });
        menuBar.add(mnConsumo);
        
        mnConsulta = new JMenu("Consulta");
        mnConsulta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaConsulta();
            }
        });
        menuBar.add(mnConsulta);
    }
}