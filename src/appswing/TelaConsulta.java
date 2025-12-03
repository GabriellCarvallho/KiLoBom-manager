package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import fachada.Fachada;
import modelo.Consumo;
import modelo.Cliente;

public class TelaConsulta {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnConsultar;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JComboBox<String> cmbConsulta;

    public TelaConsulta() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Consultas Específicas");
        frame.setBounds(100, 100, 800, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 43, 740, 200);
        frame.getContentPane().add(scrollPane);

        table = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };

        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(Color.LIGHT_GRAY);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        lblMensagem = new JLabel("");
        lblMensagem.setForeground(Color.BLUE);
        lblMensagem.setBounds(21, 370, 740, 14);
        frame.getContentPane().add(lblMensagem);

        lblResultados = new JLabel("resultados:");
        lblResultados.setBounds(21, 260, 500, 14);
        frame.getContentPane().add(lblResultados);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = cmbConsulta.getSelectedIndex();
                if(index < 0) {
                    lblResultados.setText("consulta não selecionada");
                } else {
                    lblResultados.setText("");
                    switch(index) {
                        case 0: // consumos na data X
                            String dataStr = JOptionPane.showInputDialog("Digite a data (aaaa-mm-dd):");
                            if (dataStr != null && !dataStr.trim().isEmpty()) {
                                try {
                                    List<Consumo> resultado1 = Fachada.buscarConsumosData(dataStr);
                                    listagemConsumo(resultado1);
                                } catch (Exception ex) {
                                    lblMensagem.setText("Erro: " + ex.getMessage());
                                }
                            }
                            break;
                            
                        case 1: // consumos do cliente X
                            String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente:");
                            if (cpf != null && !cpf.trim().isEmpty()) {
                                try {
                                    List<Consumo> resultado2 = Fachada.buscarConsumosCliente(cpf);
                                    listagemConsumo(resultado2);
                                } catch (Exception ex) {
                                    lblMensagem.setText("Erro: " + ex.getMessage());
                                }
                            }
                            break;
                            
                        case 2: // clientes que consumiram em mais de N filiais
                            String nStr = JOptionPane.showInputDialog("Digite N:");
                            if (nStr != null && !nStr.trim().isEmpty()) {
                                try {
                                    int n = Integer.parseInt(nStr);
                                    List<Cliente> resultado3 = Fachada.buscarConsumouEmNFiliais(n);
                                    listagemCliente(resultado3);
                                } catch (NumberFormatException ex) {
                                    lblMensagem.setText("N deve ser um número inteiro");
                                } catch (Exception ex) {
                                    lblMensagem.setText("Erro: " + ex.getMessage());
                                }
                            }
                            break;
                    }
                }
            }
        });
        btnConsultar.setBounds(600, 10, 100, 25);
        frame.getContentPane().add(btnConsultar);

        cmbConsulta = new JComboBox<String>();
        cmbConsulta.setToolTipText("selecione a consulta");
        cmbConsulta.setModel(new DefaultComboBoxModel<>(
            new String[] {
                "Consumos na data X",
                "Consumos do cliente X", 
                "Clientes que consumiram em mais de N filiais"
            }));
        cmbConsulta.setBounds(21, 10, 500, 25);
        frame.getContentPane().add(cmbConsulta);
    }

    public void listagemConsumo(List<Consumo> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("ID");
            model.addColumn("Filial ID");
            model.addColumn("Cliente");
            model.addColumn("Data");
            model.addColumn("Descrição");
            model.addColumn("Valor");

            for (Consumo c : lista) {
                model.addRow(new Object[] { 
                    c.getId(), 
                    c.getFilial().getId(),
                    c.getCliente().getNome(),
                    c.getData(),
                    c.getDescricao(),
                    String.format("R$ %.2f", c.getValorpago())
                });
            }
            
            lblResultados.setText("Resultados: " + lista.size() + " consumos encontrados");
            lblMensagem.setText("");
            
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }

    public void listagemCliente(List<Cliente> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("CPF");
            model.addColumn("Nome");
            model.addColumn("Qtd Consumos");
            model.addColumn("Qtd Filiais");

            for (Cliente c : lista) {
                // Contar filiais distintas
                java.util.Set<Integer> filiaisUnicas = new java.util.HashSet<>();
                for (Consumo consumo : c.getConsumos()) {
                    filiaisUnicas.add(consumo.getFilial().getId());
                }
                    
                model.addRow(new Object[] { 
                    c.getCpf(), 
                    c.getNome(),
                    c.getConsumos().size(),
                    filiaisUnicas.size()
                });
            }
            
            lblResultados.setText("Resultados: " + lista.size() + " clientes encontrados");
            lblMensagem.setText("");
            
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }
}