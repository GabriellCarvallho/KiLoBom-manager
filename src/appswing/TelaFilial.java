package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import fachada.Fachada;
import modelo.Filial;
import modelo.Consumo;
import modelo.Localizacao;

public class TelaFilial {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCriar;
    private JButton btnAtualizar;
    private JButton btnApagar;
    private JButton btnLimpar;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JLabel lblId;
    private JLabel lblNome;
    private JLabel lblLatitude;
    private JLabel lblLongitude;
    private JLabel lblConsumos;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtLatitude;
    private JTextField txtLongitude;
    private JTextField txtConsumos;
    
    public TelaFilial() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(false);
        frame.setModal(true);
        frame.setTitle("Gerenciar Filiais");
        frame.setBounds(100, 100, 700, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                listagem();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 39, 650, 150);
        frame.getContentPane().add(scrollPane);

        table = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    lblMensagem.setText("");
                    if (table.getSelectedRow() >= 0) {
                        int id = (int) table.getValueAt(table.getSelectedRow(), 0);
                        // Encontrar filial na lista
                        List<Filial> filiais = Fachada.listarFiliais();
                        Filial filialSelecionada = null;
                        for (Filial f : filiais) {
                            if (f.getId() == id) {
                                filialSelecionada = f;
                                break;
                            }
                        }
                        
                        if (filialSelecionada != null) {
                            txtId.setText(String.valueOf(filialSelecionada.getId()));
                            txtNome.setText(filialSelecionada.getNome());
                            txtLatitude.setText(String.valueOf(filialSelecionada.getLocalizacao().getLatitude()));
                            txtLongitude.setText(String.valueOf(filialSelecionada.getLocalizacao().getLongitude()));
                            
                            // Listar consumos da filial
                            StringBuilder consumosStr = new StringBuilder();
                            for (Consumo cons : filialSelecionada.getConsumos()) {
                                consumosStr.append("ID:").append(cons.getId())
                                          .append(" (").append(cons.getCliente().getNome())
                                          .append("), ");
                            }
                            if (consumosStr.length() > 0) {
                                consumosStr.setLength(consumosStr.length() - 2);
                            } else {
                                consumosStr.append("Nenhum consumo");
                            }
                            txtConsumos.setText(consumosStr.toString());
                        }
                    }
                } catch (Exception erro) {
                    lblMensagem.setText("Erro: " + erro.getMessage());
                }
            }
        });

        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        lblMensagem = new JLabel("");
        lblMensagem.setForeground(Color.RED);
        lblMensagem.setBounds(21, 420, 650, 14);
        frame.getContentPane().add(lblMensagem);

        lblResultados = new JLabel("selecione uma filial para editar");
        lblResultados.setBounds(21, 200, 400, 14);
        frame.getContentPane().add(lblResultados);

        lblId = new JLabel("ID:");
        lblId.setHorizontalAlignment(SwingConstants.RIGHT);
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblId.setBounds(21, 230, 60, 14);
        frame.getContentPane().add(lblId);

        lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNome.setBounds(21, 260, 60, 14);
        frame.getContentPane().add(lblNome);

        lblLatitude = new JLabel("Latitude:");
        lblLatitude.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLatitude.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblLatitude.setBounds(21, 290, 60, 14);
        frame.getContentPane().add(lblLatitude);

        lblLongitude = new JLabel("Longitude:");
        lblLongitude.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLongitude.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblLongitude.setBounds(21, 320, 60, 14);
        frame.getContentPane().add(lblLongitude);

        lblConsumos = new JLabel("Consumos:");
        lblConsumos.setHorizontalAlignment(SwingConstants.RIGHT);
        lblConsumos.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblConsumos.setBounds(21, 350, 60, 14);
        frame.getContentPane().add(lblConsumos);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtId.setBounds(91, 226, 80, 20);
        frame.getContentPane().add(txtId);

        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtNome.setColumns(10);
        txtNome.setBounds(91, 256, 250, 20);
        frame.getContentPane().add(txtNome);

        txtLatitude = new JTextField();
        txtLatitude.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtLatitude.setColumns(10);
        txtLatitude.setBounds(91, 286, 150, 20);
        frame.getContentPane().add(txtLatitude);

        txtLongitude = new JTextField();
        txtLongitude.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtLongitude.setColumns(10);
        txtLongitude.setBounds(91, 316, 150, 20);
        frame.getContentPane().add(txtLongitude);

        txtConsumos = new JTextField();
        txtConsumos.setEditable(false);
        txtConsumos.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtConsumos.setColumns(10);
        txtConsumos.setBounds(91, 346, 400, 20);
        frame.getContentPane().add(txtConsumos);

        btnCriar = new JButton("Criar");
        btnCriar.setToolTipText("cadastrar nova filial");
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarFilial();
            }
        });
        btnCriar.setBounds(21, 380, 80, 25);
        frame.getContentPane().add(btnCriar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setToolTipText("atualizar filial");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarFilial();
            }
        });
        btnAtualizar.setBounds(111, 380, 90, 25);
        frame.getContentPane().add(btnAtualizar);

        btnApagar = new JButton("Apagar");
        btnApagar.setToolTipText("apagar filial e seus consumos");
        btnApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarFilial();
            }
        });
        btnApagar.setBounds(211, 380, 80, 25);
        frame.getContentPane().add(btnApagar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtId.setText("");
                txtNome.setText("");
                txtLatitude.setText("");
                txtLongitude.setText("");
                txtConsumos.setText("");
            }
        });
        btnLimpar.setBounds(301, 380, 80, 25);
        frame.getContentPane().add(btnLimpar);

        frame.setVisible(true);
    }

    public void listagem() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("ID");
            model.addColumn("Nome");
            model.addColumn("Latitude");
            model.addColumn("Longitude");
            model.addColumn("Qtd Consumos");

            List<Filial> lista = Fachada.listarFiliais();
            for (Filial f : lista) {
                model.addRow(new Object[] { 
                    f.getId(),
                    f.getNome(),
                    f.getLocalizacao().getLatitude(),
                    f.getLocalizacao().getLongitude(),
                    f.getConsumos().size() 
                });
            }

            lblResultados.setText("Resultados: " + lista.size() + " filiais - selecione para editar");
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }

    private void criarFilial() {
        try {
            if (txtNome.getText().isEmpty() || txtLatitude.getText().isEmpty() || txtLongitude.getText().isEmpty()) {
                lblMensagem.setText("Nome, Latitude e Longitude são obrigatórios");
                return;
            }
            
            String nome = txtNome.getText().trim();
            double latitude = Double.parseDouble(txtLatitude.getText().trim());
            double longitude = Double.parseDouble(txtLongitude.getText().trim());
            
            Localizacao localizacao = new Localizacao(latitude, longitude);
            Fachada.criarFilial(nome, localizacao);
            lblMensagem.setText("Filial criada com sucesso!");
            listagem();
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage());
        }
    }

    private void atualizarFilial() {
        try {
            if (txtId.getText().isEmpty()) {
                lblMensagem.setText("Selecione uma filial primeiro");
                return;
            }
            
            int id = Integer.parseInt(txtId.getText());
            double latitude = Double.parseDouble(txtLatitude.getText().trim());
            double longitude = Double.parseDouble(txtLongitude.getText().trim());
            
            Localizacao novaLocalizacao = new Localizacao(latitude, longitude);
            Fachada.atualizarFilial(id, novaLocalizacao);
            lblMensagem.setText("Filial atualizada com sucesso!");
            listagem();
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage());
        }
    }

    private void apagarFilial() {
        try {
            if (txtId.getText().isEmpty()) {
                lblMensagem.setText("Selecione uma filial primeiro");
                return;
            }
            
            int id = Integer.parseInt(txtId.getText());
            
            Object[] options = { "Confirmar", "Cancelar" };
            int escolha = JOptionPane.showOptionDialog(null, 
                    "Esta operação apagará a filial '" + txtNome.getText() + "' e todos os seus consumos", 
                    "Alerta", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                    null, options, options[1]);
                    
            if (escolha == 0) {
                Fachada.deletarFilial(id);
                lblMensagem.setText("Filial excluída!");
                listagem();
                txtId.setText("");
                txtNome.setText("");
                txtLatitude.setText("");
                txtLongitude.setText("");
                txtConsumos.setText("");
            } else {
                lblMensagem.setText("Exclusão cancelada");
            }
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }
}