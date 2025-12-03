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
import modelo.Cliente;
import modelo.Consumo;

public class TelaCliente {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCriar;
    private JButton btnAtualizar;
    private JButton btnApagar;
    private JButton btnLimpar;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JLabel lblCpf;
    private JLabel lblNome;
    private JLabel lblConsumos;
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtConsumos;
    
    public TelaCliente() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(false);
        frame.setModal(true);
        frame.setTitle("Gerenciar Clientes");
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
                        String cpf = (String) table.getValueAt(table.getSelectedRow(), 0);
                        // Encontrar cliente na lista
                        List<Cliente> clientes = Fachada.listarClientes();
                        Cliente clienteSelecionado = null;
                        for (Cliente c : clientes) {
                            if (c.getCpf().equals(cpf)) {
                                clienteSelecionado = c;
                                break;
                            }
                        }
                        
                        if (clienteSelecionado != null) {
                            txtCpf.setText(clienteSelecionado.getCpf());
                            txtNome.setText(clienteSelecionado.getNome());
                            
                            // Listar consumos do cliente
                            StringBuilder consumosStr = new StringBuilder();
                            for (Consumo cons : clienteSelecionado.getConsumos()) {
                                consumosStr.append("ID:").append(cons.getId())
                                          .append(" (").append(cons.getDescricao())
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
                    lblMensagem.setText(erro.getMessage());
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

        lblResultados = new JLabel("selecione um cliente para editar");
        lblResultados.setBounds(21, 200, 400, 14);
        frame.getContentPane().add(lblResultados);

        lblCpf = new JLabel("CPF:");
        lblCpf.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblCpf.setBounds(21, 230, 60, 14);
        frame.getContentPane().add(lblCpf);

        lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblNome.setBounds(21, 260, 60, 14);
        frame.getContentPane().add(lblNome);

        lblConsumos = new JLabel("Consumos:");
        lblConsumos.setHorizontalAlignment(SwingConstants.RIGHT);
        lblConsumos.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblConsumos.setBounds(21, 290, 60, 14);
        frame.getContentPane().add(lblConsumos);

        txtCpf = new JTextField();
        txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtCpf.setColumns(10);
        txtCpf.setBounds(91, 226, 150, 20);
        frame.getContentPane().add(txtCpf);

        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtNome.setColumns(10);
        txtNome.setBounds(91, 256, 250, 20);
        frame.getContentPane().add(txtNome);

        txtConsumos = new JTextField();
        txtConsumos.setEditable(false);
        txtConsumos.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtConsumos.setColumns(10);
        txtConsumos.setBounds(91, 286, 350, 20);
        frame.getContentPane().add(txtConsumos);

        btnCriar = new JButton("Criar");
        btnCriar.setToolTipText("cadastrar novo cliente");
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarCliente();
            }
        });
        btnCriar.setBounds(21, 350, 80, 25);
        frame.getContentPane().add(btnCriar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setToolTipText("atualizar cliente");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarCliente();
            }
        });
        btnAtualizar.setBounds(111, 350, 90, 25);
        frame.getContentPane().add(btnAtualizar);

        btnApagar = new JButton("Apagar");
        btnApagar.setToolTipText("apagar cliente e seus consumos");
        btnApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarCliente();
            }
        });
        btnApagar.setBounds(211, 350, 80, 25);
        frame.getContentPane().add(btnApagar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtCpf.setText("");
                txtNome.setText("");
                txtConsumos.setText("");
            }
        });
        btnLimpar.setBounds(301, 350, 80, 25);
        frame.getContentPane().add(btnLimpar);

        frame.setVisible(true);
    }

    public void listagem() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("CPF");
            model.addColumn("Nome");
            model.addColumn("Qtd Consumos");

            List<Cliente> lista = Fachada.listarClientes();
            for (Cliente c : lista) {
                model.addRow(new Object[] { 
                    c.getCpf(), 
                    c.getNome(), 
                    c.getConsumos().size() 
                });
            }

            lblResultados.setText("Resultados: " + lista.size() + " clientes - selecione para editar");
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }

    private void criarCliente() {
        try {
            if (txtCpf.getText().isEmpty() || txtNome.getText().isEmpty()) {
                lblMensagem.setText("CPF e Nome são obrigatórios");
                return;
            }
            
            String cpf = txtCpf.getText().trim();
            String nome = txtNome.getText().trim();
            
            Fachada.criarCliente(cpf, nome);
            lblMensagem.setText("Cliente criado com sucesso!");
            listagem();
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage());
        }
    }

    private void atualizarCliente() {
        try {
            if (txtCpf.getText().isEmpty()) {
                lblMensagem.setText("Selecione um cliente primeiro");
                return;
            }
            
            String cpf = txtCpf.getText().trim();
            String nome = txtNome.getText().trim();
            
            Fachada.atualizarCliente(cpf, nome);
            lblMensagem.setText("Cliente atualizado com sucesso!");
            listagem();
        } catch (Exception ex) {
            lblMensagem.setText("Erro: " + ex.getMessage());
        }
    }

    private void apagarCliente() {
        try {
            if (txtCpf.getText().isEmpty()) {
                lblMensagem.setText("Selecione um cliente primeiro");
                return;
            }
            
            String cpf = txtCpf.getText();
            
            Object[] options = { "Confirmar", "Cancelar" };
            int escolha = JOptionPane.showOptionDialog(null, 
                    "Esta operação apagará o cliente " + txtNome.getText() + " e todos os seus consumos", 
                    "Alerta", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
                    null, options, options[1]);
                    
            if (escolha == 0) {
                Fachada.deletarCliente(cpf);
                lblMensagem.setText("Cliente excluído!");
                listagem();
                txtCpf.setText("");
                txtNome.setText("");
                txtConsumos.setText("");
            } else {
                lblMensagem.setText("Exclusão cancelada");
            }
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }
}