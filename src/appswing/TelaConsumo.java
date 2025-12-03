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
import javax.swing.JComboBox;
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
import modelo.Consumo;
import modelo.Cliente;
import modelo.Filial;

public class TelaConsumo {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCriar;
    private JButton btnAtualizar;
    private JButton btnApagar;
    private JButton btnLimpar;
    private JButton btnListar;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JLabel lblId;
    private JLabel lblFilial;
    private JLabel lblCliente;
    private JLabel lblData;
    private JLabel lblDescricao;
    private JLabel lblValor;
    private JTextField txtId;
    private JComboBox<String> cmbFilial;
    private JComboBox<String> cmbCliente;
    private JTextField txtData;
    private JTextField txtDescricao;
    private JTextField txtValor;
    
    public TelaConsumo() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Gerenciar Consumos");
        frame.setBounds(100, 100, 800, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                listagem();
                carregarCombos();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 45, 740, 150);
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
                    if (table.getSelectedRow() >= 0) {
                        int id = (int) table.getValueAt(table.getSelectedRow(), 0);
                        // Encontrar consumo na lista
                        List<Consumo> consumos = Fachada.listarConsumos();
                        Consumo consumoSelecionado = null;
                        for (Consumo c : consumos) {
                            if (c.getId() == id) {
                                consumoSelecionado = c;
                                break;
                            }
                        }
                        
                        if (consumoSelecionado != null) {
                            txtId.setText(String.valueOf(consumoSelecionado.getId()));
                            
                            // Filial
                            String filialStr = "ID: " + consumoSelecionado.getFilial().getId() + 
                                             " (Lat: " + consumoSelecionado.getFilial().getLocalizacao().getLatitude() +
                                             ", Long: " + consumoSelecionado.getFilial().getLocalizacao().getLongitude() + ")";
                            cmbFilial.setSelectedItem(filialStr);
                            
                            // Cliente
                            String clienteStr = consumoSelecionado.getCliente().getNome() + 
                                               " (" + consumoSelecionado.getCliente().getCpf() + ")";
                            cmbCliente.setSelectedItem(clienteStr);
                            
                            txtData.setText(consumoSelecionado.getData());
                            txtDescricao.setText(consumoSelecionado.getDescricao());
                            txtValor.setText(String.valueOf(consumoSelecionado.getValorpago()));
                            
                            lblMensagem.setText("");
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
        lblMensagem.setBounds(21, 470, 740, 14);
        frame.getContentPane().add(lblMensagem);

        btnListar = new JButton("Listar");
        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
                carregarCombos();
            }
        });
        btnListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnListar.setBounds(350, 11, 100, 23);
        frame.getContentPane().add(btnListar);

        lblResultados = new JLabel("selecione um consumo para editar");
        lblResultados.setBounds(21, 200, 400, 14);
        frame.getContentPane().add(lblResultados);

        lblId = new JLabel("ID:");
        lblId.setHorizontalAlignment(SwingConstants.RIGHT);
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblId.setBounds(21, 225, 60, 14);
        frame.getContentPane().add(lblId);

        lblFilial = new JLabel("Filial:");
        lblFilial.setHorizontalAlignment(SwingConstants.RIGHT);
        lblFilial.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblFilial.setBounds(21, 255, 60, 14);
        frame.getContentPane().add(lblFilial);

        lblCliente = new JLabel("Cliente:");
        lblCliente.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblCliente.setBounds(21, 285, 60, 14);
        frame.getContentPane().add(lblCliente);

        lblData = new JLabel("Data:");
        lblData.setHorizontalAlignment(SwingConstants.RIGHT);
        lblData.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblData.setBounds(300, 225, 60, 14);
        frame.getContentPane().add(lblData);

        lblDescricao = new JLabel("Descrição:");
        lblDescricao.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblDescricao.setBounds(300, 255, 60, 14);
        frame.getContentPane().add(lblDescricao);

        lblValor = new JLabel("Valor R$:");
        lblValor.setHorizontalAlignment(SwingConstants.RIGHT);
        lblValor.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblValor.setBounds(300, 285, 60, 14);
        frame.getContentPane().add(lblValor);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtId.setBounds(91, 221, 80, 20);
        frame.getContentPane().add(txtId);

        cmbFilial = new JComboBox<>();
        cmbFilial.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cmbFilial.setBounds(91, 251, 180, 22);
        frame.getContentPane().add(cmbFilial);

        cmbCliente = new JComboBox<>();
        cmbCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cmbCliente.setBounds(91, 281, 180, 22);
        frame.getContentPane().add(cmbCliente);

        txtData = new JTextField();
        txtData.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtData.setBounds(370, 221, 120, 20);
        txtData.setText("aaaa-mm-dd");
        frame.getContentPane().add(txtData);

        txtDescricao = new JTextField();
        txtDescricao.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtDescricao.setBounds(370, 251, 200, 20);
        frame.getContentPane().add(txtDescricao);

        txtValor = new JTextField();
        txtValor.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtValor.setBounds(370, 281, 100, 20);
        frame.getContentPane().add(txtValor);

        btnCriar = new JButton("Criar");
        btnCriar.setToolTipText("cadastrar novo consumo");
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarConsumo();
            }
        });
        btnCriar.setBounds(21, 350, 80, 25);
        frame.getContentPane().add(btnCriar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setToolTipText("atualizar consumo");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarConsumo();
            }
        });
        btnAtualizar.setBounds(111, 350, 90, 25);
        frame.getContentPane().add(btnAtualizar);

        btnApagar = new JButton("Apagar");
        btnApagar.setToolTipText("apagar consumo");
        btnApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarConsumo();
            }
        });
        btnApagar.setBounds(211, 350, 80, 25);
        frame.getContentPane().add(btnApagar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        btnLimpar.setBounds(301, 350, 80, 25);
        frame.getContentPane().add(btnLimpar);

        frame.setVisible(true);
    }

    private void carregarCombos() {
        try {
            cmbFilial.removeAllItems();
            cmbCliente.removeAllItems();
            
            List<Filial> filiais = Fachada.listarFiliais();
            List<Cliente> clientes = Fachada.listarClientes();
            
            for (Filial f : filiais) {
                String filialStr = "ID: " + f.getId() + 
                                 " (Lat: " + f.getLocalizacao().getLatitude() +
                                 ", Long: " + f.getLocalizacao().getLongitude() + ")";
                cmbFilial.addItem(filialStr);
            }
            
            for (Cliente c : clientes) {
                cmbCliente.addItem(c.getNome() + " (" + c.getCpf() + ")");
            }
            
            if (cmbFilial.getItemCount() > 0) cmbFilial.setSelectedIndex(0);
            if (cmbCliente.getItemCount() > 0) cmbCliente.setSelectedIndex(0);
            
        } catch (Exception e) {
            lblMensagem.setText("Erro ao carregar dados: " + e.getMessage());
        }
    }

    public void listagem() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("ID");
            model.addColumn("Filial ID");
            model.addColumn("Cliente");
            model.addColumn("Data");
            model.addColumn("Valor");

            List<Consumo> lista = Fachada.listarConsumos();
            
            for (Consumo c : lista) {
                model.addRow(new Object[] { 
                    c.getId(), 
                    c.getFilial().getId(),
                    c.getCliente().getNome(),
                    c.getData(),
                    String.format("R$ %.2f", c.getValorpago())
                });
            }

            lblResultados.setText("Resultados: " + lista.size() + " consumos - selecione para editar");
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }

    private void criarConsumo() {
        try {
            if (cmbFilial.getSelectedIndex() < 0 || cmbCliente.getSelectedIndex() < 0) {
                lblMensagem.setText("Selecione filial e cliente");
                return;
            }
            
            // Extrair ID da filial do texto do combo
            String filialSelecionada = (String) cmbFilial.getSelectedItem();
            int idFilial = Integer.parseInt(filialSelecionada.substring(
                filialSelecionada.indexOf("ID: ") + 4,
                filialSelecionada.indexOf(" (Lat:")
            ).trim());
            
            // Extrair CPF do cliente do texto do combo
            String clienteSelecionado = (String) cmbCliente.getSelectedItem();
            String cpfCliente = clienteSelecionado.substring(
                clienteSelecionado.indexOf("(") + 1,
                clienteSelecionado.indexOf(")")
            );
            
            String data = txtData.getText();
            String descricao = txtDescricao.getText();
            double valor = Double.parseDouble(txtValor.getText());
            
            Fachada.criarConsumo(data, descricao, valor, idFilial, cpfCliente);
            lblMensagem.setText("Consumo criado com sucesso!");
            listagem();
            limparCampos();
            carregarCombos();
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage());
        }
    }

    private void atualizarConsumo() {
        try {
            if (txtId.getText().isEmpty()) {
                lblMensagem.setText("Selecione um consumo primeiro");
                return;
            }
            
            int id = Integer.parseInt(txtId.getText());
            String data = txtData.getText();
            String descricao = txtDescricao.getText();
            double valor = Double.parseDouble(txtValor.getText());
            
            Fachada.atualizarConsumo(id, data, descricao, valor);
            lblMensagem.setText("Consumo atualizado com sucesso!");
            listagem();
            limparCampos();
            carregarCombos();
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage());
        }
    }

    private void apagarConsumo() {
        try {
            if (txtId.getText().isEmpty()) {
                lblMensagem.setText("Selecione um consumo primeiro");
                return;
            }
            
            int id = Integer.parseInt(txtId.getText());
            
            Object[] options = { "Confirmar", "Cancelar" };
            int escolha = JOptionPane.showOptionDialog(null, 
                    "Confirma exclusão do consumo ID: " + id, 
                    "Alerta", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                    null, options, options[1]);
                    
            if (escolha == 0) {
                Fachada.deletarConsumo(id);
                lblMensagem.setText("Consumo excluído!");
                listagem();
                limparCampos();
                carregarCombos();
            } else {
                lblMensagem.setText("Exclusão cancelada");
            }
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtData.setText("aaaa-mm-dd");
        txtDescricao.setText("");
        txtValor.setText("");
        if (cmbFilial.getItemCount() > 0) cmbFilial.setSelectedIndex(0);
        if (cmbCliente.getItemCount() > 0) cmbCliente.setSelectedIndex(0);
    }
}