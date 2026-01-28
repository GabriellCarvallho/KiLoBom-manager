package com.rede.kilobom.appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.rede.kilobom.fachada.Fachada;
import com.rede.kilobom.modelo.Cliente;
import com.rede.kilobom.modelo.Consumo;

public class TelaCliente {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCriar;
    private JButton btnAtualizar;
    private JButton btnApagar;
    private JButton btnLimpar;
    private JButton btnBuscarFoto;
    private JButton btnLimparFoto;
    private JLabel lblMensagem;
    private JLabel lblResultados;
    private JLabel lblCpf;
    private JLabel lblNome;
    private JLabel lblConsumos;
    private JLabel lblFoto;
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtConsumos;

    private BufferedImage image;

    public TelaCliente() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(false);
        frame.setModal(true);
        frame.setTitle("Gerenciar Clientes");
        frame.setBounds(100, 100, 700, 550);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
                        Cliente clienteSelecionado = Fachada.localizarCliente(cpf);

                        if (clienteSelecionado != null) {
                            txtCpf.setText(clienteSelecionado.getCpf());
                            txtNome.setText(clienteSelecionado.getNome());

                            // Listar consumos
                            StringBuilder consumosStr = new StringBuilder();
                            for (Consumo cons : clienteSelecionado.getConsumos()) {
                                consumosStr.append("ID:").append(cons.getId())
                                           .append(" (").append(cons.getDescricao())
                                           .append("), ");
                            }
                            if (consumosStr.length() > 0)
                                consumosStr.setLength(consumosStr.length() - 2);
                            else
                                consumosStr.append("Nenhum consumo");
                            txtConsumos.setText(consumosStr.toString());

                            // Carregar foto
                            if (clienteSelecionado.getFoto() != null) {
                                InputStream in = new ByteArrayInputStream(clienteSelecionado.getFoto());
                                image = ImageIO.read(in);
                                ImageIcon icon = new ImageIcon(image.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
                                lblFoto.setIcon(icon);
                                lblFoto.setText("");
                            } else {
                                image = null;
                                lblFoto.setIcon(null);
                                lblFoto.setText("Sem foto");
                            }
                        }
                    }
                } catch (Exception erro) {
                    lblMensagem.setText("Erro: " + erro.getMessage());
                }
            }
        });

        table.setGridColor(Color.BLACK);
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
        lblMensagem.setBounds(21, 480, 650, 14);
        frame.getContentPane().add(lblMensagem);

        lblResultados = new JLabel("Selecione um cliente para editar");
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

        lblFoto = new JLabel("Sem foto");
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setBorder(new LineBorder(Color.BLACK));
        lblFoto.setBounds(500, 230, 120, 120);
        frame.getContentPane().add(lblFoto);

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
        btnCriar.setBounds(21, 350, 80, 25);
        btnCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarCliente();
            }
        });
        frame.getContentPane().add(btnCriar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(111, 350, 90, 25);
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarCliente();
            }
        });
        frame.getContentPane().add(btnAtualizar);

        btnApagar = new JButton("Apagar");
        btnApagar.setBounds(211, 350, 80, 25);
        btnApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarCliente();
            }
        });
        frame.getContentPane().add(btnApagar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(301, 350, 80, 25);
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtCpf.setText("");
                txtNome.setText("");
                txtConsumos.setText("");
                image = null;
                lblFoto.setIcon(null);
                lblFoto.setText("Sem foto");
            }
        });
        frame.getContentPane().add(btnLimpar);

        btnBuscarFoto = new JButton("Buscar Foto");
        btnBuscarFoto.setBounds(500, 360, 120, 25);
        btnBuscarFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = selecionarArquivoFoto();
                if (file == null) return;
                try {
                    image = ImageIO.read(file);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
                    lblFoto.setIcon(icon);
                    lblFoto.setText("");
                    lblMensagem.setText("Não esqueça de atualizar/criar cliente para salvar a foto");
                } catch (IOException ex) {
                    lblMensagem.setText("Erro ao carregar foto: " + ex.getMessage());
                }
            }
        });
        frame.getContentPane().add(btnBuscarFoto);

        btnLimparFoto = new JButton("Limpar Foto");
        btnLimparFoto.setBounds(500, 390, 120, 25);
        btnLimparFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                image = null;
                lblFoto.setIcon(null);
                lblFoto.setText("Sem foto");
                lblMensagem.setText("Não esqueça de atualizar/criar cliente para salvar a foto");
            }
        });
        frame.getContentPane().add(btnLimparFoto);

        frame.setVisible(true);
    }

    private void listagem() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("CPF");
            model.addColumn("Nome");
            model.addColumn("Qtd Consumos");

            List<Cliente> lista = Fachada.listarClientes();
            for (Cliente c : lista) {
                model.addRow(new Object[] { c.getCpf(), c.getNome(), c.getConsumos().size() });
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

            // Salvar foto se houver
            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                Fachada.alterarFoto(cpf, baos.toByteArray());
            }

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

            // Atualizar foto
            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                Fachada.alterarFoto(cpf, baos.toByteArray());
            } else {
                Fachada.alterarFoto(cpf, null); // remover foto
            }

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
                image = null;
                lblFoto.setIcon(null);
                lblFoto.setText("Sem foto");
            } else {
                lblMensagem.setText("Exclusão cancelada");
            }
        } catch (Exception erro) {
            lblMensagem.setText("Erro: " + erro.getMessage());
        }
    }

    private File selecionarArquivoFoto() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif");
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
